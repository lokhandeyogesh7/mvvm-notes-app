package com.example.notesappdemomvvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

open class NoteViewModel(application: Application) : AndroidViewModel(application) {

    var repository: NoteRepository
    private var allNotes: LiveData<List<Note>>? = null

    init {
        repository = NoteRepository(application)
        allNotes = repository.getAllNote()
    }

    fun insert(note: Note) {
        repository.insert(note)
    }

    fun update(note: Note) {
        repository.update(note)
    }

    fun delete(note: Note) {
        repository.delete(note)
    }

    fun deleteAllData() {
        repository.deleteAllNotes()
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes!!
    }

}