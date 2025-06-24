package com.example

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType

import org.junit.jupiter.api.Test

@QuarkusTest
class EarthquakeResourceTest {

    @Test
    fun `test get nearby earthquakes returns OK`() {
        given()
            .queryParam("lat", 42.6975)
            .queryParam("lon", 23.3242)
            .queryParam("radius_km", 100)
            .`when`()
            .get("/api/earthquakes/nearby")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
    }

    @Test
    fun `test missing query params returns 400`() {
        given()
            .`when`()
            .get("/api/earthquakes/nearby")
            .then()
            .statusCode(400)
    }
}
