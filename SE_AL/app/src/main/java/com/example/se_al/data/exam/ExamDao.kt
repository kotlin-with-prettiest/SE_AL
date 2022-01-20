package com.example.se_al.data.exam

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ExamDao {
    @Insert
    fun insert(exam: Exam)

    @Update
    fun update(exam: Exam)

    @Query("SELECT * FROM Exam")
    fun getAll(): List<Exam>
}