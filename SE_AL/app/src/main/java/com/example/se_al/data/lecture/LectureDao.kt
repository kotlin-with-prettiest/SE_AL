package com.example.se_al.data.lecture

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LectureDao {
    @Insert
    fun insert(lecture: Lecture)

    @Query("SELECT * FROM Lecture")
    fun getAll(): List<Lecture>

    // 특정 과목의 강의 주차 목록을 가져옴
    @Query("select * from Lecture where course_id = :courseId")
    fun getLectureInCourse(courseId: String): List<Lecture>
}