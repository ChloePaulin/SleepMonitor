package fr.simsa.sleepmonitor.data.lights

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import fr.simsa.sleepmonitor.models.LightVariationEvent
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object LightRepository {

    fun uploadSleepDataToFirebase(events: List<LightVariationEvent>) {
        val user = FirebaseAuth.getInstance().currentUser
        val db = FirebaseFirestore.getInstance()
        if (user == null) {
            println("Utilisateur non connecté.")
            return
        }

        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        val data = mapOf(
            "userId" to user.uid,
            "date" to date,
            "variationLightCount" to events.size,
            "lightVariations" to events.map {
                mapOf("timestamp" to it.timestamp, "lux" to it.lux)
            }
        )

        db.collection("sleep_sessions")
            .add(data)
            .addOnSuccessListener { println("Données enregistrées avec succès dans Firebase.") }
            .addOnFailureListener { e -> println("Erreur Firebase : ${e.message}") }
    }
}
