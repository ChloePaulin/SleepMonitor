package fr.simsa.sleepmonitor.data.users

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
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
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            // Récupération de l'utilisateur Firebase Auth. Si null, création échouée.
            // Retourne une exception si null (avec Result.failure).
            val firebaseUser = authResult.user
                ?: return Result.failure(Exception("Échec de création du compte"))

            val user = User(
                id = firebaseUser.uid,
                username = username,
                email = email
            )

            // Crée ou modifie l'utilisateur et récupère son UUID (document).
            usersCollection.document(firebaseUser.uid).set(user).await()
            // Renvoie un succès avec l'objet utilisateur.
            Result.success(user)

            // Toutes les lignes ci-dessous gèrent les erreurs de résultat.
            // TODO : pour toutes les erreurs créer une boîte de dialogue pour indiquer l'erreur à l'utilisateur
        } catch (e: FirebaseAuthException) {
            Result.failure(Exception("Erreur FirebaseAuth (${e.errorCode}) : ${e.message}"))
        } catch (e: FirebaseFirestoreException) {
            Result.failure(Exception("Erreur Firestore : ${e.message}"))
        } catch (e: Exception) {
            Result.failure(Exception("Erreur inconnue : ${e.message}"))
        }
    }

    /**
     * Connexion d'un utilisateur avec Firebase Auth
     */
    suspend fun loginUser(email: String, password: String): Result<User> {

        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user
                ?: return Result.failure(Exception("FirebaseAuth: utilisateur null"))

            val userDoc = usersCollection.document(firebaseUser.uid).get().await()

            // Si le document (l'UUID) de l'utilisateur existe, retourne l'utilisateur.
            // TODO : pour toutes les erreurs créer une boîte de dialogue pour indiquer l'erreur à l'utilisateur
            if (userDoc.exists()) {
                val user = userDoc.toObject(User::class.java)
                if (user != null) {
                    Result.success(user)
                } else {
                    Result.failure(Exception("Document utilisateur invalide"))
                }
            } else {
                Result.failure(Exception("Profil utilisateur introuvable"))
            }

            // Toutes les lignes ci-dessous gèrent les erreurs de résultat.
            // TODO : pour toutes les erreurs créer une boîte de dialogue pour indiquer l'erreur à l'utilisateur
        } catch (e: FirebaseAuthException) {
            Result.failure(Exception("Erreur FirebaseAuth (${e.errorCode}) : ${e.message}"))
        } catch (e: Exception) {
            Result.failure(Exception("Erreur inconnue : ${e.message}"))
        }
    }

    /**
     * Obtenir l'utilisateur actuellement connecté
     */
    suspend fun getCurrentUser(): User? {
        val firebaseUser = auth.currentUser ?: return null
        return try {
            val document = usersCollection.document(firebaseUser.uid).get().await()
            document.toObject(User::class.java)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Mettre à jour le profil utilisateur (username, email, password)
     */
    suspend fun updateUser(
        user: User,
        newEmail: String? = null,
        newPassword: String? = null
    ): Result<User> {
        return try {
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
            val updatedUser = user.copy(email = newEmail ?: user.email)
            Result.success(updatedUser)

            // Toutes les lignes ci-dessous gèrent les erreurs de résultat.
            // TODO : pour toutes les erreurs créer une boîte de dialogue pour indiquer l'erreur à l'utilisateur
        } catch (e: FirebaseAuthException) {
            Result.failure(Exception("Erreur de mise à jour Auth : ${e.message}"))
        } catch (e: Exception) {
            Result.failure(Exception("Erreur : ${e.message}"))
        }
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
    suspend fun deleteUser(): Result<Unit> {
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
}
