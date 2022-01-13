package com.example.se_al.data.content

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Content(
    var content_id: String,
    var parent_id: String,
    var menu_name: String,
    var title: String,
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
