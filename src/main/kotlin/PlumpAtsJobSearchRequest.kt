package org.blueliner

class PlumpAtsJobSearchRequest(
    val page: Int,
    val size: Int,
    val applyResumeTags: Boolean = false,
    val sortField: String = "externalUpdatedAt",
    val includeJobsWithoutSalary: Boolean = true,
    val applyRecommendations: Boolean = false
) 