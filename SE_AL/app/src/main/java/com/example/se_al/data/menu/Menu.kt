package com.example.se_al.data.menu

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Menu(
    var course_id: String,
    var course_name: String,
    var menu_name: String,
    var menu_id: String
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
