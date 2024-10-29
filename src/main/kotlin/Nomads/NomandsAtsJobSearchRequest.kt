package org.blueliner.Nomads

data class JobSearchRequest(
    var track_total_hits: Boolean,
    var from: Long,
    var size: Long,
    val sort: List<Sort> = listOf(Sort.Premium(), Sort.PubDate()),
    val _source: List<String> = listOf(
        "company", "company_slug", "category_name", "locations", "location_base",
        "salary_range", "salary_range_short", "number_of_applicants", "instructions",
        "id", "external_id", "slug", "title", "pub_date", "tags", "source",
        "apply_option", "apply_email", "apply_url", "premium", "expired", "use_ats",
        "position_type", "annual_salary_usd"
    )
) {
    data class Query(
        val bool: Bool = Bool()
    ) {
        data class Bool(
            val filter: List<Filter> = listOf(Filter())
        ) {
            data class Filter(
                val terms: Terms = Terms()
            ) {
                data class Terms(
                    val locations: List<String> = listOf("Anywhere", "North America", "USA")
                )
            }
        }
    }

    sealed class Sort {
        data class Premium(val premium: Order = Order()) : Sort()
        data class PubDate(val pub_date: Order = Order()) : Sort()

        data class Order(
            val order: String = "desc"
        )
    }
}