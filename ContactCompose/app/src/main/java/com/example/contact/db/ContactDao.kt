package com.example.contact.db

import androidx.room.*
import com.example.contact.model.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllContact(contact: List<Contact>)

    @Query("SELECT * FROM contact ORDER BY name ASC")
    fun getAllContact(): Flow<List<Contact>>

    @Query("DELETE FROM contact")
    suspend fun deleteAllContact()

    @Query("SELECT * FROM contact WHERE isFavorite = true")
    fun getFavoriteContacts(): Flow<List<Contact>>

    @Query("SELECT * FROM contact WHERE id = :id")
    suspend fun getContact(id: String): Contact

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact)

//    @Delete
//    suspend fun deleteContact(id: String)

}