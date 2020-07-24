package com.anil.notebook.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val priority: Int? = null){
    constructor(title:String?, description: String?, priority: Int?)
            : this(null, title, description, priority)

    override fun equals(other: Any?): Boolean {

        if (javaClass != other?.javaClass){
            return false
        }

        other as Note

        if (id != other.id){
            return false
        }
        if (title != other.title){
            return false
        }
        if (description != other.description){
            return false
        }
        if (priority != other.priority){
            return false
        }
        return true
    }
}