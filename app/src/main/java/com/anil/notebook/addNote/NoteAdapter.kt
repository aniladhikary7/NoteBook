package com.anil.notebook.addNote

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anil.notebook.R
import com.anil.notebook.database.Note

class NoteAdapter(private var listener: OnItemClickListener) : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    private var notes: List<Note> = ArrayList<Note>()

    class NoteHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.text_view_title)
        var description: TextView = view.findViewById(R.id.text_view_description)
        var priority: TextView = view.findViewById(R.id.text_view_priority)
        var noteItemView: RelativeLayout = view.findViewById(R.id.note_item_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return NoteHolder(view)
    }

    override fun getItemCount(): Int = notes.size


    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        notes[position]?.let { currentNote ->
            with(holder) {
                title.text = currentNote.title
                description.text = currentNote.description
                priority.text = currentNote.priority.toString()
                noteItemView.setOnClickListener {
                    val adapterPosition: Int = adapterPosition
                    if (listener != null && adapterPosition != RecyclerView.NO_POSITION){
                        listener.onItemClick(notes[adapterPosition])
                    }
                }
            }
        }
    }

    fun submitNotes(notes: List<Note>){
        this.notes = notes
        notifyDataSetChanged()
    }

    fun getNoteAt(position: Int): Note{
        return notes[position]
    }

    interface OnItemClickListener{
        fun onItemClick(note: Note)
    }
}