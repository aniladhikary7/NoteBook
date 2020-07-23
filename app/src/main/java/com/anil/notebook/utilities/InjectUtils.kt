package com.anil.notebook.utilities

import android.content.Context
import com.anil.notebook.database.NoteDatabase
import com.anil.notebook.repo.NoteRepository
import com.anil.notebook.viewModel.NoteViewModelFactory

object InjectUtils {

    private fun getNoteRepository(context: Context): NoteRepository {
        return NoteRepository.getInstance(
            NoteDatabase.getInstance(context.applicationContext).noteDao(),
            AppExecutors.getInstance())
    }

    fun provideNoteViewModelFactory(context: Context): NoteViewModelFactory {
        val repository = getNoteRepository(context)
        return NoteViewModelFactory(repository)
    }
}