package fr.simsa.sleepmonitor.data.users

import java.text.SimpleDateFormat
import java.util.Locale
import com.google.firebase.firestore.FirebaseFirestore
import fr.simsa.sleepmonitor.models.User
import kotlinx.coroutines.tasks.await
import java.util.Date
import java.util.UUID

object UserRepository {
    private val db = FirebaseFirestore.getInstance()

    // Inscription utilisateur
    suspend fun registerUser(username: String, email: String, password: String): User? {
        return try {
            val id = UUID.randomUUID().toString()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val createdAt = dateFormat.format(Date())

            val user = User(
                id = id,
                username = username,
                email = email,
                password = password,
                createdAt = createdAt
            )

            db.collection("users").document(id).set(user).await()
            user
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Connexion utilisateur
    suspend fun loginUser(email: String, password: String): User? {
        return try {
            val snapshot = db.collection("users")
                .whereEqualTo("email", email)
                .whereEqualTo("password", password)
                .get()
                .await()

            if (!snapshot.isEmpty) {
                snapshot.documents.first().toObject(User::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun updateUser(user: User): Boolean {
        return try {
            db.collection("users").document(user.id).set(user).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}