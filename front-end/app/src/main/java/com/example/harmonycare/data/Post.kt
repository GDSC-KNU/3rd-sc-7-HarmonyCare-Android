package com.example.harmonycare.data

import java.time.LocalDateTime

data class Post (
    var title: String,
    var caption: String,
    var comments: Int,
    var postTime: LocalDateTime,
    var author: String,
    var type: Int
)
// type
// 1 : Parenting Question
// 2 : Parenting Diary