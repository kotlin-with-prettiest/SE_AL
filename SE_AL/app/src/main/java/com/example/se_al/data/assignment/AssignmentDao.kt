package com.example.se_al.data.assignment

import androidx.room.*

@Dao
interface AssignmentDao {
    // 과제를 직접 추가할 때
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(assignment: Assignment)

    @Update
    fun update(assignment: Assignment)

    @Query("SELECT * FROM Assignment")
    fun getAll(): List<Assignment>

    @Query("select * from Assignment where course_id = :courseId")
    fun getAssignmentsInCourse(courseId: String): List<Assignment>
}