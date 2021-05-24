package com.raultorinz.simpletodo.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
class Task {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idTask")
    var id : Long = 0

    @ColumnInfo(name = "completed")
    var completed : Boolean = false

    @ColumnInfo(name = "name")
    var name : String? = null
    var description : String? = null

    @ColumnInfo(name = "createdDate")
    var dateTask: String? = null

    constructor(){}

    constructor(completed: Boolean, name: String, description: String, created: String) {
        this.completed = completed
        this.name = name
        this.description = description
        this.dateTask = created
    }
}
