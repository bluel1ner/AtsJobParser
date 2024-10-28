package org.blueliner

class PlumpJobRequest(
    private val ids: List<String>? ,
    private val titleQuery: String? ,
    private val companyNameQuery: String? ,
    private val companyName: String? ,
    private val categories: List<String>? ,
    private val regions: List<String>? ,
    private val subRegions: List<String>? ,
    private val countries: List<String>? ,
    private val states: List<String>? ,
    private val cities: List<String>? ,
    private val attendance: String? ,
    private val companyBenefits: List<String>? ,
    private val experienceLevel: String? ,
    private val tags: List<String>? ,
    private val employmentType: String?,
    private val salaryMin: Int?,
    private val salaryMax: Int?,
    private val page: Int = 0,
    private val size: Int = 10,
    private val applyResumeTags: Boolean = false,
    private val sortField: String = "externalUpdatedAt",
    private val onlyFavouriteCompanies: Boolean? ,
    private val includeJobsWithoutSalary: Boolean = true,
    private val applyRecommendations: Boolean = false
) 