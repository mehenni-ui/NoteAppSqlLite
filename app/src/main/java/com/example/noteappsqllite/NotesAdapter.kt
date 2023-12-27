package com.example.noteappsqllite

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class NotesAdapter(private val context: Context, private var notes: List<Note>): RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    val noteDatabaseHelper = NoteDatabaseHelper(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item_layout, parent,false)
        return NotesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = notes[position]
        holder.binding(note)
        holder.btnEdit.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateNoteActivity::class.java).apply {
                putExtra("note_id", note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.btnDelete.setOnClickListener {
            noteDatabaseHelper.deleteNote(note.id)
            refreshList(noteDatabaseHelper.getALlNotes())
            Toast.makeText(holder.itemView.context, "Note has been deleted", Toast.LENGTH_LONG).show()
        }
    }

    inner class NotesViewHolder(itemView: View): ViewHolder(itemView){
        private val title: TextView = itemView.findViewById(R.id.tvNoteTitle)
        private val description: TextView = itemView.findViewById(R.id.tvNoteDescription)
        val btnEdit: ImageView = itemView.findViewById(R.id.btnEditNote)
        val btnDelete: ImageView = itemView.findViewById(R.id.btnDeleteNote)
        fun binding(note: Note){
            title.text = note.title
            description.text = note.description
        }
    }

    fun refreshList(notes: List<Note>){
        this.notes = notes
        notifyDataSetChanged()
    }
}