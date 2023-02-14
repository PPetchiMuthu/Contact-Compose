package com.example.android.contact.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.android.contact.repository.Converter

@Database(entities = [Contact::class], version = 2, exportSchema = false)
@TypeConverters(Converter::class)
abstract class ContactDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    companion object {
        @Volatile
        private var INSTANCE: ContactDatabase? = null
        fun getInstance(context: Context): ContactDatabase {
            synchronized(this) {
                var instace = INSTANCE
                if (instace == null) {
                    instace = Room.databaseBuilder(
                        context.applicationContext,
                        ContactDatabase::class.java,
                        "contact_details"
                    ).fallbackToDestructiveMigration().build()
                }
                INSTANCE = instace
                return instace
            }
        }
    }
}