package com.example

import jakarta.annotation.security.RolesAllowed
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.persistence.EntityManager
import jakarta.ws.rs.*
import jakarta.ws.rs.core.*
import java.sql.Timestamp
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import io.vertx.core.http.HttpServerRequest
import jakarta.transaction.Transactional


@Path("/api/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
class AdminAuditResource {

    @Inject
    lateinit var entityManager: EntityManager

    @Inject
    lateinit var request: HttpServerRequest

     var logger = Logger()

    @GET
    @Path("/admin/audit-report")
    @RolesAllowed("ADMIN")
    @Transactional
    fun getAuditReport(
        @QueryParam("start") start: LocalDate,
        @QueryParam("end") end: LocalDate,
        @QueryParam("filter") filter: String?
    ): Response {
        // Валидация за <= 3 месеца
        if (ChronoUnit.MONTHS.between(start, end) > 3) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Максималният период е 3 месеца").build()
        }

        val username = request.getHeader("X-Remote-User")?: "unknown"
        logger.logAudit(entityManager,request,username, "audit-report", "Requested audit logs from $start to $end, filter: $filter")

        // Изграждане на заявка според филтър...
        val query = buildAuditQuery(start, end, filter)

        val rawResults = entityManager.createNativeQuery(query).resultList

        val mappedResults = rawResults.map {
            val row = it as Array<*>
            AuditLogEntry(
                username = row[0] as String,
                ip_address = row[1] as String,
                event_time = (row[2] as Timestamp).toLocalDateTime(),
                data_type = row[3] as String,
                details = row[4] as String
            )
        }

        return Response.ok(mappedResults).build()
    }

    fun buildAuditQuery(start: LocalDate, end: LocalDate, filter: String?): String {
        val baseQuery = StringBuilder(
            """
        SELECT username, ip_address, event_time, data_type, details
        FROM audit_logs
        WHERE event_time BETWEEN '$start' AND '$end'
    """
        )

        if (!filter.isNullOrBlank()) {
            // Примерен пълен текстов филтър върху всички текстови полета
            val lowerFilter = filter.trim().lowercase()
            baseQuery.append(
                """
            AND (
                LOWER(username) LIKE '%$lowerFilter%' OR
                LOWER(ip_address) LIKE '%$lowerFilter%' OR
                LOWER(details) LIKE '%$lowerFilter%'
            )
        """
            )
        }

        baseQuery.append(" ORDER BY event_time DESC")
        return baseQuery.toString()
    }
}

