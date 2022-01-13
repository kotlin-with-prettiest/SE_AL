package com.example.se_al.data.course

import androidx.room.*

@Dao
interface CourseDao {
    @Insert
    fun insert(course: Course)

    @Update
    fun update(course: Course)

    @Delete
    fun delete(course: Course)

    @Query("SELECT * FROM Course")
    fun getAll(): List<Course>

    @Query("DELETE FROM Course WHERE course_name = :name")
    fun deleteCourseByName(name: String)

    // 수강하고 있는 모든 과목을 가져옴
    @Query("SELECT * FROM Course")
    fun getAllCourses(): List<Course>
}