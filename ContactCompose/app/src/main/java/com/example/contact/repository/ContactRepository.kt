package com.example.contact.repository

import android.app.Application
import com.example.contact.db.ContactDao
import com.example.contact.model.Contact
import com.example.contact.use_case.ContactUseCase
import com.example.contact.util.networkBoundResource
import kotlinx.coroutines.flow.Flow

class ContactRepository(
    private val dao: ContactDao,
    private val useCase: ContactUseCase,
    private val app: Application
) {

    fun getAllContact() = networkBoundResource(
        query = {
            dao.getAllContact()
        },
        fetch = {
            useCase.getContactsUseCase.invoke(app)
        },
        saveFetchResult = { contact: List<Contact> ->
            dao.deleteAllContact()
            dao.insertAllContact(contact)
        }
    )

    fun getFavoriteContacts(): Flow<List<Contact>> = dao.getFavoriteContacts()

    suspend fun getContact(id: String) = dao.getContact(id = id)

    suspend fun insertContact(contact: Contact) {
        useCase.insertContactUseCase.invoke(contact = contact, context = app)
        dao.insertContact(contact)
    }

//    suspend fun deleteContact(contact: Contact) {
//        dao.deleteContact(contact.id)
//        useCase.deleteUseCase.invoke(app, contact.phoneNumber!!)
//    }

}