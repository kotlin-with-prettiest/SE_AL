package com.example.se_al.data.lecture

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Lecture(
    var course_id: String,
    var course_name: String,
    var lecture_week: String,
    var lecture_id: String
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
