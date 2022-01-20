package com.example.se_al.data.calendar

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Calendar(
    @PrimaryKey var source_id: String,
    var course_name: String,
    var start_date: String,
    var end_date: String,
    var title: String,
    var type: String
)
