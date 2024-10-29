package org.blueliner.Nomads

data class NomandsAtsJobSearchResponse(
    val took: Int = 0,
    val timed_out: Boolean = false,
    val _shards: Shards = Shards(),
    val hits: Hits = Hits()
) {
    data class Shards(
        val total: Int = 0,
        val successful: Int = 0,
        val skipped: Int = 0,
        val failed: Int = 0
    )

    data class Hits(
        val total: Total = Total(),
        val max_score: Double? = null,
        val hits: List<Hit> = emptyList()
    ) {
        data class Total(
            val value: Int = 0,
            val relation: String = ""
        )

        data class Hit(
            val _index: String = "",
            val _id: String = "",
            val _score: Double? = null,
            val _source: Source = Source(),
            val sort: List<Long> = emptyList()
        ) {
            data class Source(
                val id: Int = 0,
                val title: String = "",
                val slug: String = "",
                val company: String = "",
                val companySlug: String = "",
                val categoryName: String = "",
                val positionType: String = "",
                val tags: List<String> = emptyList(),
                val locations: List<String> = emptyList(),
                val locationBase: String = "",
                val pubDate: String = "",
                val applyOption: String = "",
                val applyEmail: String = "",
                val apply_url: String = "",
                val externalId: String = "",
                val source: String = "",
                val premium: Boolean = false,
                val instructions: String = "",
                val expired: Boolean = false,
                val useAts: Boolean = false,
                val salaryRange: String = "",
                val salaryRangeShort: String = "",
                val annualSalaryUsd: Double? = null,
                val numberOfApplicants: Int = 0
            )
        }
    }
}