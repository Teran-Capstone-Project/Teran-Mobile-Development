package com.example.teran.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.teran.data.model.Journal

@Dao
interface JournalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(journal: Journal)

    @Update
    fun update(journal: Journal)

    @Delete
    fun delete(journal: Journal)

    @Query("SELECT * FROM journal ORDER BY id DESC")
    fun getAll(): LiveData<List<Journal>>

    @Query("SELECT * FROM journal WHERE `desc` LIKE :query")
    fun search(query: String?): LiveData<List<Journal>>

}