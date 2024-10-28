package org.blueliner

fun main() {
    val plumpClient = PlumpClient()
    val set = mutableSetOf<String>()
    for(i in 1..100) {
        val plumpAtsJobSearchRequest = PlumpAtsJobSearchRequest(size = 20, page = i)
        val plumpJobResponse = plumpClient.getJobsFromClient(plumpAtsJobSearchRequest)
        for(it in  plumpJobResponse.jobs) {
            set.add(it.applyUrl)
        }
    }
    println(set.size)

}


