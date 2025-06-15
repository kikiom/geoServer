package com.example

data class EarthquakeFeatureCollection(
    val type: String = "FeatureCollection",
    val features: List<EarthquakeFeature>
)

data class EarthquakeFeature(
    val type: String = "Feature",
    val properties: EarthquakeProperties,
    val geometry: Geometry
)

data class EarthquakeProperties(
    val id: String,
    val magnitude: Double?,
    val place: String?,
    val event_time: String
)

data class Geometry(
    val type: String = "Point",
    val coordinates: List<Double>
)
