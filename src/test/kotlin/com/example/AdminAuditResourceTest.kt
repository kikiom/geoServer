package com.example

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType

import org.junit.jupiter.api.Test

@QuarkusTest
class AdminAuditResourceTest {

    @Test
    fun `test audit report returns data for admin`() {
        val token = getAdminToken()

        given()
            .auth().oauth2(token)
            .queryParam("start", "2025-05-01")
            .queryParam("end", "2025-06-25")
            .`when`()
            .get("/api/admin/audit-report")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
    }

    private fun getAdminToken(): String {
        // Use Keycloak test container or mocked JWT here
        return "mocked-admin-token"
    }
}
