package com.example.se_al.data.children_content

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChildrenContent(
    var parent_id: String,
    var content_id: String,
    var title: String,
    var created: String,
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
