package com.example.se_al.data.announcement

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Announcement(
    var course_id: String,
    var course_name: String,
    @PrimaryKey var announcement_id: String,
    var title: String,
    var body: String?
)