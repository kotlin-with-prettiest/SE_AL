package com.example.se_al.data.sub_lecture

import androidx.room.*

@Dao
interface SubLectureDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(subLecture: SubLecture)

    @Update
    fun update(subLecture: SubLecture)

    @Query("SELECT * FROM SubLecture")
    fun getAll(): List<SubLecture>

    // 특정 주차에 대한 컨텐츠를 가져옴
    @Query("select * from SubLecture where parent_id = :parentId")
    fun getSubLectureInWeek(parentId: String): List<SubLecture>
}