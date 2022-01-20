package com.example.se_al.data.children_content

import androidx.room.*

@Dao
interface ChildrenContentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(childrenContent: ChildrenContent)

    @Update
    fun update(childrenContent: ChildrenContent)

    @Delete
    fun delete(childrenContent: ChildrenContent)

    @Query("SELECT * FROM ChildrenContent")
    fun getAll(): List<ChildrenContent>

    @Query("DELETE FROM ChildrenContent WHERE title = :title")
    fun deleteChildContentByTitle(title: String)
}