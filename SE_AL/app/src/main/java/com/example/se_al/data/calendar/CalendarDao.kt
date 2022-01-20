package com.example.se_al.data.calendar

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.util.TableInfo

@Dao
interface CalendarDao {
    @Insert
    fun insert(calendar: Calendar)

    @Update
    fun update(calendar: Calendar)

    @Query("SELECT * FROM Calendar")
    fun getAll(): List<Calendar>

    @Query("select start_date from Calendar where source_id = :sourceId")
    fun getStartDate(sourceId: String): String

    @Query("select end_date from Calendar where source_id = :sourceId")
    fun getEndDate(sourceId: String): String
}