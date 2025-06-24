package com.example

import jakarta.persistence.*
import java.time.Instant
import java.time.LocalDateTime

data class AuditLogEntry(
    val username: String,
    val ip_address: String,
    val event_time: LocalDateTime,
    val data_type: String,
    val details: String
)

@Entity
@Table(name = "audit_logs")
data class AuditLog(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val username: String,
    val ipAddress: String,
    val eventType: String,
    val eventTime: Instant,

    @Column(columnDefinition = "TEXT")
    val details: String
)