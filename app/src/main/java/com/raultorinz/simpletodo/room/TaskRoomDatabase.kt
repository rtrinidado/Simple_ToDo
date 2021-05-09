package com.raultorinz.simpletodo.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(Task::class)], version = 1)
abstract class TaskRoomDatabase : RoomDatabase(){
    abstract fun taskDao() : TaskDao

    companion object {
        @Volatile
        private var INSTANCE : TaskRoomDatabase? = null

        fun getDatabase(context: Context) : TaskRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                        TaskRoomDatabase::class.java,
                        "task_database").build()
                INSTANCE = instance
                instance
            }
        }
    }
}