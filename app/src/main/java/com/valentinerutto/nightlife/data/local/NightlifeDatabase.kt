package com.valentinerutto.nightlife.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [EventEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class NightlifeDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao

    companion object Companion {
        @Volatile
        private var INSTANCE: NightlifeDatabase? = null
        fun getDatabase(context: Context): NightlifeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, NightlifeDatabase::class.java, "nightlife_database"
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration(false).build()
                INSTANCE = instance
                instance
            }
        }
    }
}