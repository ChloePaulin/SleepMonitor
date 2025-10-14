package fr.simsa.sleepmonitor.data.api

import fr.simsa.sleepmonitor.models.TranslateRequest
import fr.simsa.sleepmonitor.services.TranslateService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TranslateAPI{
    private val baseURL = "https://fr.libretranslate.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(TranslateService::class.java)

    suspend fun translateText(text: String, source: String = "en", target: String = "fr"): String? {
        return try {
            val request = TranslateRequest(q = text, source = source, target = target)
            val response = service.translate(request)
            if (response.isSuccessful) {
                response.body()?.translatedText
            } else null
        } catch (e: Exception) {
            println("Erreur traduction: ${e.message}")
            null
        }
    }
}