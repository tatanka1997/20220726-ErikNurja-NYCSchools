package com.example.a20220726_eriknurja_nycschools.api

import com.example.a20220726_eriknurja_nycschools.model.NYCSchool
import com.example.a20220726_eriknurja_nycschools.model.NYCScore
import com.example.a20220726_eriknurja_nycschools.utils.SCHOOL_ENDPOINT
import com.example.a20220726_eriknurja_nycschools.utils.SCORE_ENDPOINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SchoolApiService {
    @GET(SCHOOL_ENDPOINT)
    suspend fun fetchNYCSchools(): Response<List<NYCSchool>>

    @GET(SCORE_ENDPOINT)
    suspend fun fetchNYCScore(
        @Query("dbn") dbn: String
    ): Response<List<NYCScore>>
}