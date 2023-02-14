package com.example.android.contact.repository

import androidx.lifecycle.LiveData
import com.example.android.contact.db.Contact
import com.example.android.contact.db.ContactDao

class ContactRepository(private val contactDao: ContactDao) {

    val getAllContact: LiveData<List<Contact>> = contactDao.getAllContact()

    val getFavoriteContact: LiveData<List<Contact>> = contactDao.getFavoriteContact()

    val getPersonalContact: LiveData<List<Contact>> = contactDao.getPersonalContact()

    fun addContact(contact: Contact) {
        contactDao.addContact(contact)
    }

    fun updateContact(contact: Contact) {
        contactDao.updateContact(contact)
    }

    fun getGroupContact(string: String): LiveData<List<Contact>> =
        contactDao.getGroupContact(string)

    fun searchContact(string: String): LiveData<List<Contact>> = contactDao.searchContact(string)

    fun deleteContact(contact: Contact) {
        contactDao.deleteContact(contact)
    }
}