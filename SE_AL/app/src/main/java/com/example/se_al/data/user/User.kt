package com.example.se_al.data.user

import androidx.room.Entity
import androidx.room.PrimaryKey

//Entity

@Entity
data class User(
    var user_id: String,
    var name: String,
    var bb_id: String,
    var bb_password: String,
    var course_id: List<String>
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
