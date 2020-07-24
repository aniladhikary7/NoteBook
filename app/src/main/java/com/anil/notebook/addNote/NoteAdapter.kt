package com.anil.notebook.addNote

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anil.notebook.R
import com.anil.notebook.database.Note

class NoteAdapter(val adapterOnClick: (Note) -> Unit)
    : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    inner class NoteHolder : RecyclerView.ViewHolder {

        var title: TextView
        var description: TextView
        var priority: TextView
        var noteItemView: RelativeLayout

        constructor(view: View): super(view){
            title = view.findViewById(R.id.text_view_title)
            description = view.findViewById(R.id.text_view_description)
            priority = view.findViewById(R.id.text_view_priority)
            noteItemView = view.findViewById(R.id.note_item_view)
        }
        fun setItem(note: Note) {
            noteItemView.setOnClickListener { adapterOnClick(note) }
        }
    }

    class NoteItemDiffCallBack(
        var oldNoteList: List<Note>,
        var newNoteList: List<Note>): DiffUtil.Callback(){
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldNoteList[oldItemPosition].id == newNoteList[newItemPosition].id)
        }

        override fun getOldListSize(): Int = oldNoteList.size

        override fun getNewListSize(): Int = newNoteList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldNoteList[oldItemPosition] == newNoteList[newItemPosition]
        }

    }

    private var notes: List<Note> = ArrayList<Note>()


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
                setItem(currentNote)
            }
        }
    }

    fun submitNotes(notesList: List<Note>){
        val oldNoteList = notes
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            NoteItemDiffCallBack(
                oldNoteList,
                notesList
            )
        )
        notes = notesList
        diffResult.dispatchUpdatesTo(this)
    }

    fun getNoteAt(position: Int): Note{
        return notes[position]
    }
}