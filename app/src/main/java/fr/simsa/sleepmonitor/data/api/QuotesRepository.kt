package fr.simsa.sleepmonitor.data.api

import fr.simsa.sleepmonitor.models.Quote
import fr.simsa.sleepmonitor.services.QuoteService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object QuotesRepository {

    private val baseURL: String = "https://motivational-spark-api.vercel.app/api/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(QuoteService::class.java)

    suspend fun getRandomQuote(): Quote? {
        return try {
            val response = service.getRandomQuote()
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            println(e.message)
            null
        }
    }
}
