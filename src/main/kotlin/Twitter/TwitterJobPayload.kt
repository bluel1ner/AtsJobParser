package org.blueliner.Twitter

data class TwitterJobPayload(
    val url: String,
    val companyName: String?,
    val companyUrl: String?,
    val isJobFromAts: Boolean
) {
}