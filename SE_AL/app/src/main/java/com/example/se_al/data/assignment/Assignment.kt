package com.example.se_al.data.assignment

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Assignment(
    var course_id: String,
    var course_name: String,
    var title: String,
    var body: String?,
    var memo: String?,
    @PrimaryKey var assignment_id: String,
    var source_id: String?,
    var start_date: String?,
    var end_date: String?,
    var created: String,
)
