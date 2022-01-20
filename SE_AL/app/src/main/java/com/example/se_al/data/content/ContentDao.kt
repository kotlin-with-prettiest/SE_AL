package com.example.se_al.data.content

import androidx.room.*

@Dao
interface ContentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(content: Content)

    @Update
    fun update(content: Content)

    @Delete
    fun delete(content: Content)

    @Query("SELECT * FROM Content")
    fun getAll(): List<Content>

    @Query("DELETE FROM Content WHERE title = :title")
    fun deleteContentByName(title: String)
}