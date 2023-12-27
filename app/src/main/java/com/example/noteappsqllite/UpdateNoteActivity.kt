package com.example.noteappsqllite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.noteappsqllite.databinding.ActivityUpdateNoteBinding

class UpdateNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var db: NoteDatabaseHelper
    private var noteId: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDatabaseHelper(this)

        noteId = intent.getIntExtra("note_id", -1)

        if (noteId == -1){
            finish()
            return
        }

        val note = db.getNoteById(noteId)
        binding.etUpdateTitle.setText(note.title)
        binding.etUpdateDescription.setText(note.description)


        binding.btnUpdateNote.setOnClickListener {
            val note = Note(noteId, binding.etUpdateTitle.text.toString(), binding.etUpdateDescription.text.toString())
            db.updateNOte(note)
            finish()
            Toast.makeText(this, "note has been updated", Toast.LENGTH_LONG).show()
        }
    }
}