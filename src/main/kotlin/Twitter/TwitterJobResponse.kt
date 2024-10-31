package org.blueliner.Twitter

data class TwitterJobResponse(
    val providerStatisticMap: Map<String, Int>,
    val jobsPayload: List<TwitterJobPayload>
) {
}