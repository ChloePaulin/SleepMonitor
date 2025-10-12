package fr.simsa.sleepmonitor.models

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

/**
 * Modèle utilisateur pour Firebase Auth + Firestore
 *
 * @property id UID Firebase (généré automatiquement par Firebase Auth)
 * @property username Nom d'utilisateur personnalisé
 * @property email Email (géré par Firebase Auth mais aussi stocké ici)
 * @property createdAt Date de création (timestamp Firestore)
 */
data class User(
    @DocumentId
    // UID Firebase Auth
    val id: String = "",
    val username: String = "",
    val email: String = "",
    @ServerTimestamp
    // Firestore génère automatiquement la date
    val createdAt: Date? = null
) {
    // Constructeur vide requis par Firestore
    constructor() : this("", "", "", null)
}
