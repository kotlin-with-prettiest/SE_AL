package com.example.se_al.data.exam

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExamDao {
    @Insert
    fun insert(exam: Exam)

    @Query("SELECT * FROM Exam")
    fun getAll(): List<Exam>
}