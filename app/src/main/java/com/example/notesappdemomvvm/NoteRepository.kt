package com.example.notesappdemomvvm

import android.app.Application
import androidx.lifecycle.LiveData
import android.os.AsyncTask



class NoteRepository (){
    lateinit var noteDao: NoteDao
    lateinit var allNotes:LiveData<List<Note>>

   constructor(application: Application) : this() {
        val database = NoteDatabase.getInstance(application)
        noteDao = database!!.noteDao()
        allNotes  = noteDao.getAllNotes()
    }

    fun insert(note: Note){
        InsertNoteAsyncTask(noteDao).execute(note)
    }

    fun update(note: Note){
        UpdateNoteAsyncTask(noteDao).execute(note)
    }

    fun delete(note: Note){
        DeleteNoteAsyncTask(noteDao).execute(note)
    }

    fun deleteAllNotes(){
        DeleteAllNoteAsyncTask(noteDao).execute()
    }

    fun getAllNote():LiveData<List<Note>>{

        return allNotes
    }

    class InsertNoteAsyncTask(private val noteDao: NoteDao) :
        AsyncTask<Note, Void, Void>() {

        override fun doInBackground(vararg notes: Note): Void? {
            noteDao.insert(notes[0])
            return null
        }
    }

    class UpdateNoteAsyncTask(private val noteDao: NoteDao) :
        AsyncTask<Note, Void, Void>() {

        override fun doInBackground(vararg notes: Note): Void? {
            noteDao.update(notes[0])
            return null
        }
    }

    class DeleteNoteAsyncTask(private val noteDao: NoteDao) :
        AsyncTask<Note, Void, Void>() {

        override fun doInBackground(vararg notes: Note): Void? {
            noteDao.delete(notes[0])
            return null
        }
    }

    class DeleteAllNoteAsyncTask(private val noteDao: NoteDao) :
        AsyncTask<Note, Void, Void>() {

        override fun doInBackground(vararg notes: Note): Void? {
            noteDao.deleteAllNotes()
            return null
        }
    }
}