package fr.simsa.sleepmonitor.data.users

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import fr.simsa.sleepmonitor.models.User
import kotlinx.coroutines.tasks.await

/**
 * Repository pour gérer les utilisateurs avec Firebase Auth et Firestore
 */
object UserRepository {

    /**
     * Instance Firebase Auth
     */
    private val auth: FirebaseAuth = Firebase.auth

    /**
     * Instance Firestore
     */
    private val db: FirebaseFirestore = Firebase.firestore

    /**
     * Collection des utilisateurs dans Firestore
     */
    private val usersCollection = db.collection("users")

    /**
     * Tag pour les logs d'authentification Firebase si nécessaire
     */
    private const val TAG = "FIREBASE_AUTH"

    /**
     * Enregistrement d'un nouvel utilisateur avec Firebase Auth et Firestore
     */
    suspend fun registerUser(username: String, email: String, password: String): Result<User> {
        var newUser: User? = null

        runCatching {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user
                ?: throw Exception("Échec de création du compte")

            val user = User(
                id = firebaseUser.uid,
                username = username,
                email = email
            )

            usersCollection.document(firebaseUser.uid).set(user).await()
            newUser = user
        }.recoverCatching { e ->
            println("Erreur : ${e.message}")
            return Result.failure(e)
        }
        return Result.success(newUser!!)
    }

    /**
     * Connexion d'un utilisateur avec Firebase Auth
     */
    suspend fun loginUser(email: String, password: String): Result<User> {
        var finalUser: User? = null

        runCatching {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user
                ?: throw Exception(
                    "FirebaseAuth: utilisateur null"
                )

            val userDoc = usersCollection.document(firebaseUser.uid).get().await()

            if (!userDoc.exists()) {
                throw Exception("Profil utilisateur introuvable")
            }
            val user = userDoc.toObject(User::class.java)
                ?: throw Exception("Document utilisateur invalide")
            finalUser = user
        }.recoverCatching { e ->
            println("Erreur : ${e.message}")
            return Result.failure(e)
        }
        return Result.success(finalUser!!)
    }

    /**
     * Obtenir l'utilisateur actuellement connecté
     */
    suspend fun getCurrentUser(): Result<User> {
        val firebaseUser = auth.currentUser ?: return Result.failure(
            Exception("Utilisateur non connecté")
        )

        var currentUser: User? = null

        runCatching {
            val document = usersCollection.document(firebaseUser.uid).get().await()
            currentUser = document.toObject(User::class.java)
        }.recoverCatching { e ->
            println("Erreur : ${e.message}")
            return Result.failure(e)
        }
        return Result.success(currentUser!!)
    }

    /**
     * Mettre à jour le profil utilisateur (username, email, password)
     */
    suspend fun updateUser(
        user: User,
        newEmail: String? = null,
        newPassword: String? = null
    ): Result<User> {
        var updatedUser: User? = null

        runCatching {
            val currentUser =
                auth.currentUser ?: return Result.failure(Exception("Utilisateur non connecté"))

            // Mettre à jour l'email dans Firebase Auth si modifié
            if (newEmail != null && newEmail != user.email) {
                currentUser.updateEmail(newEmail).await()
            }

            // Mettre à jour le mot de passe dans Firebase Auth si fournit
            if (!newPassword.isNullOrBlank()) {
                currentUser.updatePassword(newPassword).await()
            }

            // Mettre à jour les données dans Firestore
            val updates = hashMapOf<String, Any>(
                // "username" to user.username,
                "email" to (newEmail ?: user.email)
            )

            usersCollection.document(user.id).update(updates).await()

            // Retourner l'utilisateur mis à jour
            updatedUser = user.copy(email = newEmail ?: user.email)
        }.recoverCatching { e ->
            println("Erreur : ${e.message}")
            return Result.failure(e)
        }
            return Result.success(updatedUser!!)
    }

    /**
     * Déconnexion utilisateur
     */
    fun logout() {
        auth.signOut()
    }

    /**
     * Supprimer le compte utilisateur (Auth + Firestore)
     */
    // TODO : créer le bouton de suppression d'utilisateur dans le profil
    /*suspend fun deleteUser(): Result<Unit> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(
                // Exception pour indiquer que l'utilisateur n'est pas connecté
                Exception("Utilisateur non connecté")
            )
            // Supprimer le document Firestore
            usersCollection.document(currentUser.uid).delete().await()
            // Supprimer le compte Firebase Auth
            currentUser.delete().await()
            // Si suppression réussit ne retourne rien.
            Result.success(Unit)
        } catch (e: Exception) {
            // Exception pour indiquer que l'utilisateur n'a pas été supprimé.
            Result.failure(
                Exception("Erreur de suppression : ${e.message}")
            )
        }
    }
     */
}