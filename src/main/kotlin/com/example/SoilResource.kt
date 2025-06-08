package com.example

import jakarta.inject.Inject
import jakarta.persistence.EntityManager
import jakarta.ws.rs.*
import jakarta.ws.rs.QueryParam
import jakarta.ws.rs.core.*
import net.bytebuddy.dynamic.scaffold.TypeInitializer
import org.hibernate.jdbc.Expectation.None

@Path("/api/soil")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class SoilResource @Inject constructor(
    val entityManager: EntityManager
) {

    @GET
    @Path("/location")
    fun getSoilAtLocation(
        @QueryParam("lat") lat: Double?,
        @QueryParam("lon") lon: Double?
    ): Any? {
        if (lat == null || lon == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("Missing query parameters 'lat' or 'lon'")
                .build()
        }

        val query = """
            SELECT soil_type FROM soil_zones
            WHERE ST_Contains(geom, ST_SetSRID(ST_Point(:lon, :lat), 4326))
        """
//        val soilType = entityManager.createNativeQuery(query)
//            .setParameter("",lat)
//            .setParameter("",lon)
//            .singleResult

        return 1//Response.ok(mapOf("soil_type" to soilType)).build()
    }
}
