package com.anil.notebook.repo

import androidx.lifecycle.LiveData
import com.anil.notebook.database.Note
import com.anil.notebook.database.NoteDao
import com.anil.notebook.utilities.AppExecutors

class NoteRepository private constructor(private val noteDao: NoteDao,
                                         private val appExecutors: AppExecutors) {


    companion object {
        @Volatile
        private var instance: NoteRepository? = null
        fun getInstance(
            noteDao: NoteDao,
            appExecutors: AppExecutors
        ) =
            instance ?: synchronized(this) {
                instance ?: NoteRepository(
                    noteDao,
                    appExecutors
                )
                    .also { it }
            }
    }

    fun insert(note: Note){
        appExecutors.diskIO().execute {
            noteDao.insert(note)
        }
    }

    fun update(note: Note){
        appExecutors.diskIO().execute {
            noteDao.update(note)
        }
    }

    fun delete(note: Note){
        appExecutors.diskIO().execute {
            noteDao.delete(note)
        }
    }

    fun deleteAllNotes(){
        appExecutors.diskIO().execute {
            noteDao.deleteAll()
        }
    }

    fun getAllNotes(): LiveData<List<Note>>{
        return noteDao.getAllNotes()
    }
}