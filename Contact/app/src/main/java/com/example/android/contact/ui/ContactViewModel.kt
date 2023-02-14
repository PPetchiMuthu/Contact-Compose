package com.example.android.contact.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.android.contact.db.Contact
import com.example.android.contact.db.ContactDatabase
import com.example.android.contact.repository.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: ContactRepository
    var readAllContact: LiveData<List<Contact>>
    var readFavContact: LiveData<List<Contact>>
    var readPersonalContact: LiveData<List<Contact>>

    init {
        val contactDao = ContactDatabase.getInstance(application).contactDao()
        repository = ContactRepository(contactDao)
        readAllContact = repository.getAllContact
        readFavContact = repository.getFavoriteContact
        readPersonalContact = repository.getPersonalContact
    }

    fun addContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addContact(contact)
        }
    }

    fun searchContact(query: String): LiveData<List<Contact>> {
        return repository.searchContact(query)
    }

    fun deleteContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteContact(contact)
        }
    }

    fun updateContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateContact(contact)
        }
    }

    fun getGroupContact(string: String): LiveData<List<Contact>> {
        return repository.getGroupContact(string)
    }
}