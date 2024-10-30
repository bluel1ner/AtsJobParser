package org.blueliner.Twitter

import kotlinx.coroutines.*
import org.blueliner.countProviders
import org.blueliner.providers
import java.io.File
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

val regex = Regex("https[^,]*")
val filePath = "src/main/resources/jobs.txt"
val workers = 10
val setApplyUrls = ConcurrentHashMap.newKeySet<String?>()
val listApplyUrls = CopyOnWriteArrayList<String?>()
val outputPath = "src/main/resources/twitter_jobs.txt"

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

    File(outputPath).bufferedWriter().use { writer ->
        setApplyUrls.forEach { applyUrl ->
            writer.write(applyUrl)
            writer.newLine()
        }
    }

    val endTime = System.currentTimeMillis()
    val elapsedTime = endTime - startTime

    println("Method execution time: $elapsedTime ms")
    println(File(outputPath).readLines().size.toLong())
    println("Does set size is equal to file size: ${setApplyUrls.size.equals(File(outputPath).readLines().size.toInt())}")
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
                val applyUrl = regex.find(line)?.value
                if (applyUrl != null) {
                    setApplyUrls.add(applyUrl)
                }
            }
    }
}
