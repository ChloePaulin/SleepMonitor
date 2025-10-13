package fr.simsa.sleepmonitor.services

import fr.simsa.sleepmonitor.models.Quote
import retrofit2.Response
import retrofit2.http.GET

interface QuoteService {

    @GET("quotes/random")
    suspend fun getRandomQuote(): Response<Quote>
}
