package org.blueliner

import kotlinx.coroutines.*
import kotlin.random.Random


fun main(args: Array<String>) = runBlocking {
    println("Начало выполнения программы")

    val scope = CoroutineScope(Job() + Dispatchers.Default)
    val jobs = ArrayList<Job>()
    for (i in 1..5) {
        jobs.add(scope.launch {
            printTextWithDelay()
        })
    }

    println("Продолжение выполнения программы")

    jobs.forEach { it.join() }
//    job.join()

}

suspend fun printTextWithDelay() {
    delay(1000)
    println("Текст, напечатанный просто задержки в 3 секунды ${Thread.currentThread().name}")
}