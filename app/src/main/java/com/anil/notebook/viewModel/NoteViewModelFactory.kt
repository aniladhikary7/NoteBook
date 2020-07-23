package com.anil.notebook.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anil.notebook.repo.NoteRepository

class NoteViewModelFactory(private val noteRepository: NoteRepository):
        ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        NoteViewModel(noteRepository) as T
}