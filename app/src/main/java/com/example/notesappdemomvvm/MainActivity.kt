package com.example.notesappdemomvvm

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel
    val ADD_NOTE_RESULT = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvNotes.layoutManager = LinearLayoutManager(this@MainActivity)
        rvNotes.setHasFixedSize(true)

        var adapter = NoteAdapter()
        rvNotes.adapter = adapter

        noteViewModel = ViewModelProvider(this@MainActivity).get(NoteViewModel(application)::class.java)
        noteViewModel.getAllNotes().observe(this,
            Observer { notes ->
                adapter.setNotes(notes)
                println(
                    "observer called" +
                            ""
                )
            })

        fabAdd.setOnClickListener {
            startActivityForResult(Intent(this@MainActivity, AddNotesActivity::class.java), ADD_NOTE_RESULT)
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.adapterPosition))
                Toast.makeText(this@MainActivity, "Note deleted", Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(rvNotes)

        adapter.setOnItemClickListener(object : NoteAdapter.onItemClickListener {
            override fun onItemClick(note: Note) {
                startActivityForResult(
                    Intent(
                        this@MainActivity,
                        AddNotesActivity::class.java
                    ).putExtra(AddNotesActivity.titleName, note.title).putExtra(
                        AddNotesActivity.descripton,
                        note.description
                    ).putExtra(AddNotesActivity.priority, note.priority).putExtra("id", note.id), 101
                )
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_NOTE_RESULT && resultCode == Activity.RESULT_OK) {
            var title = data!!.getStringExtra(AddNotesActivity.titleName)
            var description = data.getStringExtra(AddNotesActivity.descripton)
            var priority = data.getIntExtra(AddNotesActivity.priority, 1)

            val note = Note(title!!, description!!, priority!!.toInt())
            noteViewModel.insert(note)

            Toast.makeText(this, "Note Added Successfully !!", Toast.LENGTH_LONG).show()
        } else if (requestCode == ADD_NOTE_RESULT && resultCode == 101) {
            var title = data!!.getStringExtra(AddNotesActivity.titleName)
            var description = data.getStringExtra(AddNotesActivity.descripton)
            var priority = data.getIntExtra(AddNotesActivity.priority, 1)
            var id = data.getIntExtra("id", -1)

            if (id==(-1)){
                Toast.makeText(this, "Note Cannot be added !!", Toast.LENGTH_LONG).show()
            }else {
                var note = Note(title!!, description!!, priority!!.toInt())
                note!!.id = id
                noteViewModel.update(note)
                Toast.makeText(this, "Note Added Successfully !!", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Unable to Add Note !!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_all_note -> {
                noteViewModel.deleteAllData()
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
