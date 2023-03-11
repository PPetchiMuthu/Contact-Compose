package com.example.contact.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.contact.model.Contact

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase : RoomDatabase() {

    abstract val contactDao: ContactDao
}