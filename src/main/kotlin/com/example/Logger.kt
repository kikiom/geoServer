package com.example

import jakarta.inject.Inject
import jakarta.persistence.EntityManager
import io.vertx.core.http.HttpServerRequest
import java.time.Instant

class Logger {

    @Inject
    lateinit var entityManager: EntityManager

    @Inject
    lateinit var request: HttpServerRequest

    fun logAudit(username: String, eventType: String, details: String) {
        val entry = AuditLog(
            username = username,
            ipAddress = getClientIp(),
            eventType = eventType,
            eventTime = Instant.now(),
            details = details
        )
        entityManager.persist(entry)
    }

    fun getClientIp(): String {
        return request.getHeader("X-Forwarded-For")
            ?: request.remoteAddress().host()
    }

}