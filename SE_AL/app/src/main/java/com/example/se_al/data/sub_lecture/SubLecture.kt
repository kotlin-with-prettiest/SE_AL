package com.example.se_al.data.sub_lecture

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SubLecture(
    var course_id: String,
    var course_name: String,
    var title: String,
    var body: String?,
    var memo: String?,
    var parent_id: String,
    var sub_lecture_id: String,
    var start_date: String?,
    var end_date: String?,
    var created: String,
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}