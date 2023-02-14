package com.example.android.contact.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactDao {

    @Insert
    fun addContact(contact: Contact)

    @Update
    fun updateContact(contact: Contact)

    @Query("SELECT * FROM contact_details ORDER BY name ASC")
    fun getAllContact(): LiveData<List<Contact>>

    @Query("SELECT * FROM contact_details WHERE favorite = 1 ORDER BY name ASC")
    fun getFavoriteContact(): LiveData<List<Contact>>

    @Query("SELECT * FROM contact_details WHERE groupContact = :group  ORDER BY name ASC")
    fun getGroupContact(group: String): LiveData<List<Contact>>

    @Query("SELECT * FROM contact_details WHERE groupContact = 'Family' OR 'Friends'  ORDER BY name ASC")
    fun getPersonalContact(): LiveData<List<Contact>>

    @Query("SELECT * FROM CONTACT_DETAILS WHERE name LIKE :string ORDER BY name ASC")
    fun searchContact(string: String): LiveData<List<Contact>>

    @Delete
    fun deleteContact(contact: Contact)

}

