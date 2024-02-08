package com.example.harmonycare.data

import java.time.LocalDateTime

data class Checklist (
    var title: String,
    var caption: String,
    var weekday: Array<String>,
    var checkTime: LocalDateTime,
    var isDone: Boolean
)