package com.example.colors

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Color::class], version = 1, exportSchema = false)
abstract class ColorDatabase : RoomDatabase() {

    abstract fun colorDao(): ColorDao

    companion object {
        @Volatile
        private var Instance: ColorDatabase? = null

        fun getInstances(context: Context): ColorDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext, ColorDatabase::class.java, "database-color")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

