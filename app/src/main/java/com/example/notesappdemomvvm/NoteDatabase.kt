package com.example.notesappdemomvvm

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    companion object {
        var instance: NoteDatabase? = null
        private set
        @Synchronized
        fun getInstance(context: Context): NoteDatabase?{
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java, "note_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            }
            return instance
        }
    }
    abstract fun noteDao(): NoteDao

    class  PopulateAsyncTask(db: NoteDatabase?) : AsyncTask<Void, Void, Void>() {
        private val noteDao: NoteDao = db!!.noteDao()
        override fun doInBackground(vararg p0: Void?): Void? {
            noteDao.insert(Note("Yolo","test",1))
            noteDao.insert(Note("Yolo","test",2))
            noteDao.insert(Note("Yolo","test",3))
            noteDao.insert(Note("Yolo","test",4))
            return null
        }
    }
}


var roomCallback = object : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        NoteDatabase.PopulateAsyncTask(NoteDatabase.instance!!).execute()
    }
}

