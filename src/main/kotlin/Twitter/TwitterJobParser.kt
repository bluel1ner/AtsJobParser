package org.blueliner.Twitter

import kotlinx.coroutines.*
import java.io.File
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList
import com.google.gson.Gson
import com.google.gson.GsonBuilder

val urlRegex = Regex("https[^,]*")
val companyNameRegex = Regex("https?://[^/]+/([^/?]+)")
val filePath = "src/main/resources/jobs.txt"
val workers = 10
val setApplyUrls = ConcurrentHashMap.newKeySet<String?>()
val outputPath = "src/main/resources/twitter_jobs.json"
val providers = listOf("greenhouse", "lever", "workable", "smartrecruiters", "ashby")

val gson: Gson = GsonBuilder().setPrettyPrinting().create()

fun main() = runBlocking {
    val scope = CoroutineScope(Job() + Dispatchers.Default)
    val jobs = ArrayList<Job>()
    val totalLines = getAmountOfFileLines(File(filePath))

    println("total size txt file $totalLines")

    val chunkSize = totalLines / workers + if (totalLines % workers > 0) 1 else 0

    for (i in 0 until workers) {
        jobs.add(scope.launch {
            readCsvLinesRangeKotlin(chunkSize * i, chunkSize)
        })
    }

    jobs.forEach { it.join() }

    println("Total set unique URLs added: ${setApplyUrls.size}")

    val startTime = System.currentTimeMillis()

    writeDataInJsonFileFormat()

    val endTime = System.currentTimeMillis()
    val elapsedTime = endTime - startTime

    println("Method execution time: $elapsedTime ms")
    println(File(outputPath).readLines().size.toLong())
    println("is set size equal to file size: ${setApplyUrls.size.equals(File(outputPath).readLines().size.toInt())}")
}

private fun writeDataInJsonFileFormat() {
    val jobs = ArrayList<TwitterJobPayload>()
    setApplyUrls.forEach { url ->
        run {
            jobs.add(createTwitterJobPayload(url))
        }
    }

    val twitterJobResponse =
        TwitterJobResponse(providerStatisticMap = countProviders(setApplyUrls, providers), jobsPayload = jobs)

    println("List size : ${jobs.size}")

    File(outputPath).bufferedWriter().use { writer ->
        writer.write(gson.toJson(twitterJobResponse))
    }
}

fun createTwitterJobPayload(applyUrl: String): TwitterJobPayload {
    val matchResult = companyNameRegex.find(applyUrl)
    var companyName = matchResult?.groupValues?.get(1)
    val companyUrl = matchResult?.groupValues?.get(0)

    var isJobFromAts: Boolean = true
    if (providers.none({ applyUrl.contains(it) })) {
        companyName = null
        isJobFromAts = false
    }

    return TwitterJobPayload(
        url = applyUrl,
        companyUrl = companyUrl,
        companyName = companyName,
        isJobFromAts = isJobFromAts
    )
}

fun getAmountOfFileLines(file: File): Long {
    return file.readLines().size.toLong()
}

suspend fun readCsvLinesRangeKotlin(startRange: Long, chunkSize: Long) {
    File(filePath).useLines { lines ->
        lines
            .drop(startRange.toInt())
            .take(chunkSize.toInt())
            .forEach { line ->
                val applyUrl = urlRegex.find(line)?.value
                if (applyUrl != null) {
                    setApplyUrls.add(applyUrl)
                }
            }
    }
}

fun countProviders(applyUrls: Set<String>, providers: List<String>): Map<String, Int> {
    val providerCount = mutableMapOf<String, Int>()

    providers.forEach { provider ->
        providerCount[provider] = 0
    }

    for (url in applyUrls) {
        for (provider in providers) {
            if (url.contains(provider, ignoreCase = true)) {
                providerCount[provider] = providerCount.getOrDefault(provider, 0) + 1
                break;
            }
        }
    }

    return providerCount
}