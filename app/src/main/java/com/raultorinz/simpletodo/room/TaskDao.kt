package com.raultorinz.simpletodo.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Query("UPDATE tasks SET completed = :completed WHERE name = :name")
    suspend fun updateTaskStatus(name: String, completed: Boolean)

    @Query("SELECT * FROM tasks")
    fun getAllTasks() : LiveData<List<Task>>
}