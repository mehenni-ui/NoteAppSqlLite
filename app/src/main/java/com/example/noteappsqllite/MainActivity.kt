package com.example.noteappsqllite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Visibility
import com.example.noteappsqllite.databinding.ActivityAddNoteBinding
import com.example.noteappsqllite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: NoteDatabaseHelper
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var notes: List<Note>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)






        binding.btnAddNote.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }

        db = NoteDatabaseHelper(this)
        getNotes()




    }


    private fun getNotes(){
        notes = db.getALlNotes()
        notesAdapter = NotesAdapter(this, notes)
        binding.rvId.layoutManager = LinearLayoutManager(this)
        binding.rvId.adapter = notesAdapter
    }

    private fun check(){
        if (db.getALlNotes().isEmpty()){
            binding.noNoteId.visibility = View.VISIBLE
        }else{
            binding.noNoteId.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        notesAdapter.refreshList(db.getALlNotes())
        check()
    }
}