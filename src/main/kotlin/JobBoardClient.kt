package org.blueliner

import org.blueliner.Nomads.JobSearchRequest
import org.blueliner.Nomads.NomandsAtsJobSearchResponse
import org.blueliner.Plump.PlumpAtsJobSearchRequest
import org.blueliner.Plump.PlumpJobResponse
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate

class JobBoardClient() {
    val restTemplate = RestTemplate()

    val headers = HttpHeaders().apply {
        set("Content-Type", "application/json")
    }



    fun getJobsFromPlumpClient(url: String, body: PlumpAtsJobSearchRequest): PlumpJobResponse {
        val httpEntity = HttpEntity(body, headers)
        return restTemplate.exchange(
            url,
            HttpMethod.POST,
            httpEntity,
            PlumpJobResponse::class.java
        ).body!!
    }

    fun getJobsFromNomandClient(url: String, body: JobSearchRequest): NomandsAtsJobSearchResponse {
        val httpEntity = HttpEntity(body, headers)
        return restTemplate.exchange(
            url,
            HttpMethod.POST,
            httpEntity,
            NomandsAtsJobSearchResponse::class.java
        ).body!!
    }


}