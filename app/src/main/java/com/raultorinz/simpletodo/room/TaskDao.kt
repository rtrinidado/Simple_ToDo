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
    fun getAllTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE name = :name")
    suspend fun getTask(name: String): Task

    @Query("DELETE FROM tasks WHERE idTask = :name")
    suspend fun deleteTask(name: String)
}