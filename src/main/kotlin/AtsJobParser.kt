package org.blueliner

import com.github.doyaaaaaken.kotlincsv.dsl.context.ExcessFieldsRowBehaviour
import com.github.doyaaaaaken.kotlincsv.dsl.context.InsufficientFieldsRowBehaviour
import org.blueliner.Nomads.JobSearchRequest
import org.blueliner.Plump.PlumpAtsJobSearchRequest
import java.io.File
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader

fun main() {
//    plumpParser()
//    nomadsParser()
    twitterParser()
//    val startTime = System.currentTimeMillis()
//    readCsvLinesRangeKotlin("", 1, 10)
//    val endTime = System.currentTimeMillis()
//    val elapsedTime = endTime - startTime
//    println("Method execution time: $elapsedTime ms")

}

fun twitterParser() {
    val filePath = "src/main/resources/jobs.txt"
    val totalLines = getAmountOfFileLines(File(filePath))
    println(totalLines)
    val workers = 10;
    File(filePath).useLines { lines ->
        {
            lines.take(10).forEach { line -> println(line) }
        }
    }
}

fun getAmountOfFileLines(file: File): Long {
    return file.readLines().size.toLong()
}

fun readCsvLinesRangeKotlin(filePath: String, startLine: Int, endLine: Int) {
    val regex = Regex("https[^,]*")
    val filePath = "src/main/resources/jobs.txt"
    var counter = 0;
    val csvFile = File(filePath).useLines { lines ->
        lines
            .drop(0)
            .take(10000)
            .forEach { line ->
                counter++
                val applyUrl = regex.find(line)?.value
                println(applyUrl)
            }
    }
    println(counter)
//    csvReader {
//        charset = "UTF-8"
//        quoteChar = '"'
//        excessFieldsRowBehaviour = ExcessFieldsRowBehaviour.IGNORE
//        insufficientFieldsRowBehaviour = InsufficientFieldsRowBehaviour.EMPTY_STRING
//    }.open(csvFile) {
//        readAllAsSequence()
//            .drop(3000)
//            .take(3100)
//            .forEach { row ->
//                val string = row.joinToString(",")
//                regex.find(string)?.let {
//                        it -> println(it.value)
//                }
//            }
//    }
}

val providers = listOf("greenhouse", "lever", "workable", "smartrecruiters", "ashby")

fun nomadsParser() {
    val url = "https://www.workingnomads.com/jobsapi/_search"
    val plumpClient = JobBoardClient()
    val applyUrls = mutableSetOf<String>()
    var from: Long = 0
    val size: Long = 50
    var batchSize: Int = -1
    while (batchSize != 0) {
        val plumpJobResponse = plumpClient.getJobsFromNomandClient(
            url,
            JobSearchRequest(from = from, size = size, track_total_hits = true)
        )

        println("from $from size $size")
        batchSize = plumpJobResponse.hits.hits.size;
        println("batchSize $batchSize")
        for (it in plumpJobResponse.hits.hits) {
            applyUrls.add(it._source.apply_url)
        }
        from += size;
    }

    val filePath = "WorkingNomads.txt"
    applyUrls.forEach { it -> println(it) }
    writeDataToFile(applyUrls, filePath)
}

private fun plumpParser() {
    val url = "https://api.plump.ai/api/search/jobs"
    val plumpJobBoardClient = JobBoardClient()
    val set = mutableSetOf<String>()
    for (i in 1..100) {
        val plumpAtsJobSearchRequest = PlumpAtsJobSearchRequest(size = 20, page = i)
        val plumpJobResponse = plumpJobBoardClient.getJobsFromPlumpClient(url, plumpAtsJobSearchRequest)
        for (it in plumpJobResponse.jobs) {
            set.add(it.applyUrl)
        }
    }
}

fun writeDataToFile(set: Set<String>, filePath: String) {
    val file = File(filePath)

    file.bufferedWriter().use { writer ->
        set.forEach { applyUrl ->
            writer.write(applyUrl)
            writer.newLine()
        }
        writer.write("Total companies amount ${set.size}")
        writer.newLine()
        countProviders(set, providers).forEach { el ->
            writer.write(el.key)
            writer.write(" ".plus(el.value.toString()))
            writer.newLine()
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
