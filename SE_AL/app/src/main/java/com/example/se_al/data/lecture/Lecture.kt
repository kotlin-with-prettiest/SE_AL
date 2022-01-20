package com.example.se_al.data.lecture

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Lecture(
    var course_id: String,
    var course_name: String,
    var lecture_week: String,
    @PrimaryKey var lecture_id: String
)