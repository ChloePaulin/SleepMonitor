package fr.simsa.sleepmonitor.services

import fr.simsa.sleepmonitor.models.TranslateRequest
import fr.simsa.sleepmonitor.models.TranslateResponse
import retrofit2.Response
import retrofit2.http.POST


interface TranslateService {
    @POST("translate")
    suspend fun translate(
        request: TranslateRequest
    ): Response<TranslateResponse>
}