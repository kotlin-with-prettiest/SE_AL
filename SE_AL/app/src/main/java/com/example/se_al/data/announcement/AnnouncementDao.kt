package com.example.se_al.data.announcement

import androidx.room.*

@Dao
interface AnnouncementDao {
    @Insert
    fun insert(announcement: Announcement)

    @Update
    fun update(announcement: Announcement)

    @Delete
    fun delete(announcement: Announcement)

    @Query("SELECT * FROM Announcement")
    fun getAll(): List<Announcement>
}