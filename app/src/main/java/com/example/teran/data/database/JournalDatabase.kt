package com.example.teran.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.teran.data.model.Journal

@Database(entities = [Journal::class], version = 1)
abstract class JournalDatabase: RoomDatabase() {

    abstract fun getJournalDao(): JournalDao

    companion object {
        @Volatile
        private var INSTANCE: JournalDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): JournalDatabase {
            if (INSTANCE == null) {
                synchronized(JournalDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        JournalDatabase::class.java,
                        "journal_db"
                    ).build()
                }
            }
            return INSTANCE as JournalDatabase
        }
    }
}