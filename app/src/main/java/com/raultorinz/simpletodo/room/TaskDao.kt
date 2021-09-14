package com.raultorinz.simpletodo.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Query("UPDATE tasks SET completed = :completed WHERE idTask = :idTask")
    suspend fun updateTaskStatus(idTask: Long, completed: Boolean)

    @Update
    suspend fun updateTask(task: Task)

    @Query("SELECT * FROM tasks WHERE completed = 1")
    fun getAllCompletedTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE completed = 0")
    fun getAllIncompletedTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE idTask = :idTask")
    suspend fun getTask(idTask: Long): Task

    @Query("DELETE FROM tasks WHERE idTask = :id")
    suspend fun deleteTask(id: Long)
}