package org.blueliner

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate

class PlumpClient() {
    val restTemplate = RestTemplate()

    val url = "https://api.plump.ai/api/search/jobs"

    val headers = HttpHeaders().apply {
        set("Content-Type", "application/json")
    }

    fun getJobsFromClient(body: PlumpAtsJobSearchRequest): PlumpJobResponse {

        val httpEntity = HttpEntity(body, headers)
        return restTemplate.exchange(
            url,
            HttpMethod.POST,
            httpEntity,
            PlumpJobResponse::class.java
        ).body!!
    }


}