package com.example.se_al.data.exam

import androidx.room.*

@Dao
interface ExamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(exam: Exam)

    @Update
    fun update(exam: Exam)

    @Query("SELECT * FROM Exam")
    fun getAll(): List<Exam>
}