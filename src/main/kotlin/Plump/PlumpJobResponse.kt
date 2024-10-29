package org.blueliner.Plump

data class PlumpJobResponse(
    val jobs: List<Job> = emptyList()
) {
    data class Job(
        val id: String = "",
        val applyUrl: String = ""
    )
}


