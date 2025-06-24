package com.example

import jakarta.inject.Inject
import jakarta.persistence.EntityManager
import io.vertx.core.http.HttpServerRequest
import jakarta.transaction.Transactional
import java.time.Instant

class Logger {

    @Transactional
    fun logAudit( entityManager:EntityManager,request: HttpServerRequest, username: String, eventType: String, details: String) {
        val entry = AuditLog(
            username = username,
            ip_address = getClientIp(request),
            event_type = eventType,
            event_time = Instant.now(),
            details = details
        )
        entityManager.persist(entry)
    }

    fun getClientIp(request: HttpServerRequest): String {
        return request.getHeader("X-Forwarded-For")
            ?: request.remoteAddress().host()
    }

}