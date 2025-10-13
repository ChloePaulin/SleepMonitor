package fr.simsa.sleepmonitor.data.api

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import fr.simsa.sleepmonitor.models.Quote
import fr.simsa.sleepmonitor.services.QuoteService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun QuotesAPI() {

    val baseURL: String = "https://motivational-spark-api.vercel.app/api/"

    var author: String by remember { mutableStateOf("") }
    var quote: String? by remember { mutableStateOf("") }

    val retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(QuoteService::class.java)

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

    suspend fun fetchedQuote() {
        return try {
            val quoteObject = getRandomQuote()

            if (quoteObject != null) {
                author = quoteObject.author
                quote = quoteObject.quote
            } else {
                author = ""
                quote = ""
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }


}
