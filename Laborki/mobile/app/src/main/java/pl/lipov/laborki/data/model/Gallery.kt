package pl.lipov.laborki.data.model

data class Gallery(
        val lat: Double,
        val lng: Double,
        val name: String,
        val overcrowdingLevel: String,
        val url: String,
)