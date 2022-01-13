package com.example.se_al.data.exam

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Exam(
    var course_id: String,
    var course_name: String,
    var title: String,
    var body: String?,
    var memo: String?,
    var exam_id: String,
    var source_id: String?,
    var start_date: String?,
    var end_date: String?,
    var created: String,
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}

