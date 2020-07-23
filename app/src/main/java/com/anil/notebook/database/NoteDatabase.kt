package com.anil.notebook.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null
        private val roomCallBack: Callback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { PopulateDbAsyncTask(it).execute() }
            }
        }

        fun getInstance(context: Context): NoteDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDatabase::class.java, "note_database"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallBack)
                        .build()
                    INSTANCE = instance
                }
                return instance
            }

        }

    }

    class PopulateDbAsyncTask : AsyncTask<Void, Void, Void> {
        private val noteDao: NoteDao

        constructor(db: NoteDatabase) {
            noteDao = db.noteDao()
        }

        override fun doInBackground(vararg params: Void): Void? {
            noteDao.insert(Note("Title 1", "Description 1", 1))
            noteDao.insert(Note("Title 2", "Description 2", 2))
            noteDao.insert(Note("Title 3", "Description 3", 3))
            return null
        }
    }
}
