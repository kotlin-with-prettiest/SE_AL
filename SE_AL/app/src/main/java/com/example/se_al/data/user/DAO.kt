package com.example.se_al.data.user

import androidx.room.*

// DAO : 데이터에 접근할 수 있는 메서드를 정의해놓은 인터페이스

@Dao
interface DAO {
    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM User")
    fun getAll(): List<User>

    @Query("DELETE FROM User WHERE name = :name")
    fun deleteUserByName(name: String)
}