package com.anil.notebook.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    fun insert(note: Note)

    @Delete
    fun delete(note: Note)

    @Update
    fun update(note: Note)

    @Query("DELETE FROM note_table")
    fun deleteAll()

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    fun getAllNotes(): LiveData<List<Note>>
}