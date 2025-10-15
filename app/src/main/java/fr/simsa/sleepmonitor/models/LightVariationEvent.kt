package fr.simsa.sleepmonitor.models

data class LightVariationEvent(
    val timestamp: Long, // System.currentTimeMillis()
    val lux: Float
)