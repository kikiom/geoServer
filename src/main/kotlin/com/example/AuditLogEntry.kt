package com.example

import jakarta.persistence.*
import java.math.BigInteger
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
    val id: Int = 0,

    val username: String,
    val ip_address: String,
    val event_type: String,
    val event_time: Instant,

    @Column(columnDefinition = "TEXT")
    val details: String
){
    constructor() : this(0, "", "", "", Instant.now(), "") // No-arg constructor
}