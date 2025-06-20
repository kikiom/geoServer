package com.example


import jakarta.inject.Inject
import jakarta.persistence.EntityManager
import jakarta.ws.rs.*
import jakarta.ws.rs.core.*

@Path("/api/earthquakes")
@Produces(MediaType.MEDIA_TYPE_WILDCARD)
@Consumes(MediaType.APPLICATION_JSON)
class EarthquakeResource @Inject constructor(
    val entityManager: EntityManager
) {

    @GET
    @Path("/nearby")
    fun getEarthquakesAtLocation(
        @QueryParam("lat") lat: Double?,
        @QueryParam("lon") lon: Double?,
        @QueryParam("radius_km") radiusKm: Double? = 50.0
    ): Response {
        if (lat == null || lon == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("Missing query parameters 'lat' or 'lon'")
                .build()
        }

        val query = """
            SELECT id, magnitude, place, event_time, longitude, latitude
            FROM earthquake_events
            WHERE ST_DWithin(
                geom::geography,
                ST_SetSRID(ST_Point(:lon, :lat), 4326)::geography,
                :radius
            )
            ORDER BY event_time DESC
            LIMIT 100
        """

        val rawResults = entityManager.createNativeQuery(query)
            .setParameter("lon", lon)
            .setParameter("lat", lat)
            .setParameter("radius", radiusKm!! * 1000)
            .resultList

        val features = rawResults.map { row ->
            val cols = row as Array<*>
            EarthquakeFeature(
                properties = EarthquakeProperties(
                    id = cols[0].toString(),
                    magnitude = (cols[1] as? Number)?.toDouble(),
                    place = cols[2] as? String,
                    event_time = cols[3].toString()
                ),
                geometry = Geometry(
                    coordinates = listOf(
                        (cols[4] as? Number)?.toDouble() ?: 0.0,
                        (cols[5] as? Number)?.toDouble() ?: 0.0
                    )
                )
            )
        }

        val geoJson = EarthquakeFeatureCollection(features = features)

        return Response.ok(geoJson).build()
//        return geoJson
//        return EarthquakeFeatureCollection(features = features)
    }
}

