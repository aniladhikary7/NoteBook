package com.anil.notebook

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.anil.notebook.addNote.AddEditNoteActivity
import com.anil.notebook.addNote.NoteAdapter
import com.anil.notebook.database.Note
import com.anil.notebook.utilities.InjectUtils
import com.anil.notebook.viewModel.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity(), View.OnClickListener, NoteAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NoteAdapter
    private lateinit var buttonAddNote: FloatingActionButton

    companion object {
        const val ADD_NOTE_REQUEST: Int = 1
        const val EDIT_NOTE_REQUEST: Int = 2
    }

    private val noteViewModel: NoteViewModel by viewModels {
        InjectUtils.provideNoteViewModelFactory(this)
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recycler_view)
        buttonAddNote = findViewById(R.id.button_add_note)
        buttonAddNote.setOnClickListener(this)
        adapter = NoteAdapter(this)
    }

    private fun initialise() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        noteViewModel.getAllNotes().observe(this, Observer {
            adapter.submitNotes(it)
        })

        ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: ViewHolder, target: ViewHolder
                ): Boolean {
                    return false // true if moved, false otherwise
                }

                override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                    // remove from adapter
                    noteViewModel.delete(adapter.getNoteAt(viewHolder.adapterPosition))
                }
            }).attachToRecyclerView(recyclerView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initialise()
    }

    override fun onClick(v: View?) {
        if (v == buttonAddNote) {
            var intent = Intent(this, AddEditNoteActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.apply {
                val title = getStringExtra(AddEditNoteActivity.EXTRA_TITLE)
                val description = getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION)
                val priority = getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1)

                var note = Note(title, description, priority)
                noteViewModel.insert(note)
            }
            Toast.makeText(this, "New note saved!", Toast.LENGTH_LONG).show()
        }else if (requestCode == EDIT_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            var id = data?.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1)
            if (id == -1){
                Toast.makeText(this, "Note can't be updated!", Toast.LENGTH_LONG).show()
                return
            }
            val title = data?.getStringExtra(AddEditNoteActivity.EXTRA_TITLE)
            val description = data?.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION)
            val priority = data?.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1)

            var note = Note(title, description, priority)
            note.id = id
            noteViewModel.update(note)
        } else {

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_all_notes -> {
                noteViewModel.deleteAllNotes()
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_LONG).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onItemClick(note: Note) {
        val intent = Intent(this, AddEditNoteActivity::class.java).apply {
            putExtra(AddEditNoteActivity.EXTRA_ID, note.id)
            putExtra(AddEditNoteActivity.EXTRA_TITLE, note.title)
            putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.description)
            putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.priority)
        }
        startActivityForResult(intent, EDIT_NOTE_REQUEST)
    }
}