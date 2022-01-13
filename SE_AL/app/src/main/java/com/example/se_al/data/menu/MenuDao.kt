package com.example.se_al.data.menu

import androidx.room.*

@Dao
interface MenuDao {
    @Insert
    fun insert(menu: Menu)

    @Update
    fun update(menu: Menu)

    @Delete
    fun delete(menu: Menu)

    @Query("SELECT * FROM Menu")
    fun getAll(): List<Menu>

    @Query("DELETE FROM Menu WHERE menu_name = :name")
    fun deleteMenuByName(name: String)

    // 특정 과목의 메뉴를 가져옴
    @Query("select * from Menu where course_id = :courseId")
    fun getMenuInCourse(courseId: String): List<Menu>
}