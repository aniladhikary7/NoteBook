package com.anil.notebook.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anil.notebook.database.Note
import com.anil.notebook.repo.NoteRepository

class NoteViewModel internal constructor(private val noteRepository: NoteRepository) : ViewModel() {

    private var allNotes: LiveData<List<Note>> = noteRepository.getAllNotes()

    override fun onCleared() {
        super.onCleared()
    }

    fun insert(note: Note) {
        noteRepository.insert(note)
    }

    fun update(note: Note) {
        noteRepository.update(note)
    }

    fun delete(note: Note) {
        noteRepository.delete(note)
    }

    fun deleteAllNotes() {
        noteRepository.deleteAllNotes()
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes
    }
}
