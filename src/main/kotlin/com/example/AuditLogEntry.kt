package com.example

import java.time.LocalDateTime

data class AuditLogEntry(
    val username: String,
    val ip_address: String,
    val event_time: LocalDateTime,
    val data_type: String,
    val details: String
)