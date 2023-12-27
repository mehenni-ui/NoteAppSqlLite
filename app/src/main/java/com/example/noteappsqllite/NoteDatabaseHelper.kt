package com.example.noteappsqllite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NoteDatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "note_database.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "note_table"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME_TITLE = "title"
        private const val COLUMN_NAME_DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val SQL_CREATE_ENTRIES =  "CREATE TABLE $TABLE_NAME ( $COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NAME_TITLE TEXT, $COLUMN_NAME_DESCRIPTION TEXT)"

        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(SQL_DELETE_ENTRIES)
    }

    fun insertNote(note: Note){
        val db = writableDatabase
        val value = ContentValues().apply {
            put(COLUMN_NAME_TITLE, note.title)
            put(COLUMN_NAME_DESCRIPTION, note.description)
        }

        db.insert(TABLE_NAME, null, value)
        db.close()
    }


    fun getALlNotes(): List<Note>{
        val notes = mutableListOf<Note>()
        val db = readableDatabase
        val q = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(q, null)
        while (cursor.moveToNext()){
            val id  = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_TITLE))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_DESCRIPTION))
            val note = Note(id, title, description)
            notes.add(note)
        }
        cursor.close()
        db.close()
        return notes
    }


    fun updateNOte(note: Note){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME_TITLE, note.title)
            put(COLUMN_NAME_DESCRIPTION, note.description)
        }

        val whereClaus = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(note.id.toString())

        db.update(TABLE_NAME, values, whereClaus, whereArgs)
        db.close()

    }

    fun getNoteById(noteId: Int): Note {
        val db = readableDatabase
        val q = "SELECT * FROM $TABLE_NAME TABLE_NAME WHERE id = $noteId"
        val cursor = db.rawQuery(q, null)
        cursor.moveToFirst()

        db.close()

        return Note(
            cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)), cursor.getString(
                cursor.getColumnIndexOrThrow(
                    COLUMN_NAME_TITLE
                )
            ), cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_DESCRIPTION))
        )
    }

    fun deleteNote(noteId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(noteId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }


}