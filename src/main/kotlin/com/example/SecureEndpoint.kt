package com.example

import jakarta.annotation.security.RolesAllowed
import jakarta.ws.rs.*
import jakarta.ws.rs.core.*

@Path("/api/secure")
class SecureEndpoint {

    @GET
    @Path("/user")
    @RolesAllowed("USER")
    fun getUserData(): String = "User-only data"

    @GET
    @Path("/admin")
    @RolesAllowed("ADMIN")
    fun getAdminData(): String = "Admin-only data"

    @GET
    @Path("/super")
    @RolesAllowed("SUPER_ADMIN")
    fun getSuperData(): String = "Super admin-only data"
}
