package com.example

import io.quarkus.hibernate.orm.panache.Panache.executeUpdate
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/hello")
class ExampleResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello() = "Hello from Quarkus REST"

//    fun upload(){
//        val query = """
//            INSERT INTO earthquake_events (...) VALUES (:id, :mag, ...)
//            ON CONFLICT (...) DO UPDATE ...
//            """
//        entityManager.createNativeQuery(query)
//            .setParameter("id", "ci41179176")
//            .setParameter("mag", 1.05)
//        ...
//        .executeUpdate()
//
//    }


}
