package com.example.se_al.data.course

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Course(
    var course_id: String,
    var course_name: String,
    var term_id: String,
    var term_name: String
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
