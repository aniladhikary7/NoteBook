package com.anil.notebook.addNote

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anil.notebook.R

class AddNoteActivity : AppCompatActivity() {

    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var numberPickerPriority: NumberPicker

    companion object{
        const val EXTRA_TITLE = "com.anil.notebook.addNote.EXTRA_TITLE"
        const val EXTRA_ID = "com.anil.notebook.addNote.EXTRA_ID"
        const val EXTRA_DESCRIPTION = "com.anil.notebook.addNote.EXTRA_DESCRIPTION"
        const val EXTRA_PRIORITY = "com.anil.notebook.addNote.EXTRA_PRIORITY"
    }

    private fun initViews(){
        editTextTitle = findViewById(R.id.add_note_title)
        editTextDescription = findViewById(R.id.add_note_description)
        numberPickerPriority = findViewById(R.id.number_picker_priority)
        numberPickerPriority.minValue = 1
        numberPickerPriority.maxValue = 10

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        val intent = intent
        if (intent.hasExtra(EXTRA_ID)){
            title = "Eit Note"
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE))
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
            numberPickerPriority.value = intent.getIntExtra(EXTRA_PRIORITY,1)
        }else{
            title = "Add Note"
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        initViews()
    }

    private fun saveNote(){
        val title: String = editTextTitle.text.toString()
        val description: String = editTextDescription.text.toString()
        val priority: Int = numberPickerPriority.value

        if (title.trim().isEmpty() || description.trim().isEmpty() ){
            Toast.makeText(this, "Fill all the fields", Toast.LENGTH_LONG).show()
            return
        }

        val data: Intent = Intent()
        data.putExtra(EXTRA_TITLE, title)
        data.putExtra(EXTRA_DESCRIPTION, description)
        data.putExtra(EXTRA_PRIORITY, priority)
        val id = intent.getIntExtra(EXTRA_ID, -1)
        if (id != -1){
            data.putExtra(EXTRA_ID, id)
        }
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.add_note_save -> {
                saveNote()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

}