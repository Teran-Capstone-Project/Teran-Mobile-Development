package com.example.teran.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.teran.data.database.dao.JournalDao
import com.example.teran.data.database.dao.PostFavoriteDao
import com.example.teran.data.model.Journal
import com.example.teran.data.model.PostFavorite

@Database(entities = [Journal::class, PostFavorite::class], version = 1)
abstract class TeranDatabase: RoomDatabase() {

    abstract fun getJournalDao(): JournalDao
    abstract fun getPostFavoriteDao(): PostFavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: TeranDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): TeranDatabase {
            if (INSTANCE == null) {
                synchronized(TeranDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        TeranDatabase::class.java,
                        "teran_db"
                    ).build()
                }
            }
            return INSTANCE as TeranDatabase
        }
    }
}