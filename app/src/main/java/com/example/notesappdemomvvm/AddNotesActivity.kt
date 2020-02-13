package com.example.notesappdemomvvm

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_notes.*
import kotlinx.android.synthetic.main.note_item.*

class AddNotesActivity : AppCompatActivity() {

    companion object {
        val titleName = "note_title"
        val descripton = "note_description"
        val priority = "note_prioirity"
    }

//    val title = "note_title"
//    val descripton = "note_description"
//    val priority = "note_prioirity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)

        npPriority.minValue = 1
        npPriority.maxValue = 10


        if (intent.hasExtra("id")) {
            supportActionBar!!.title = "Edit Note"
            tvtitle.text = intent.getStringExtra(titleName)
            tvdescription.text = intent.getStringExtra(descripton)
            npPriority.value = intent.getIntExtra(priority, 1)
        }

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        saveNote()
        return super.onOptionsItemSelected(item)
    }

    private fun saveNote() {
        var titleStr = etTitle.text.toString()
        var descriptionStr = etDescription.text.toString()
        var priorityStr = npPriority.value

        println("prioirity is " + priorityStr + npPriority.value)

        if (titleStr.isNullOrEmpty() || descriptionStr.isNullOrEmpty()) {
            Toast.makeText(this@AddNotesActivity, "Please add title and description !!", Toast.LENGTH_LONG).show()
        } else {
            var intent = Intent()
            intent.putExtra(titleName, titleStr)
            intent.putExtra(descripton, descriptionStr)
            intent.putExtra(priority, priorityStr)

            val id = intent.getIntExtra("id", -1)
            if (id != (-1)) {
                intent.putExtra("id", id)
            }

            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
