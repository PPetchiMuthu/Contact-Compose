package com.example.contact.ui.all_contact_screen.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contact.model.Contact
import com.example.contact.repository.ContactRepository
import com.example.contact.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val repository: ContactRepository
) : ViewModel() {

    private val _contactsList = MutableStateFlow<Resource<List<Contact>>>(Resource.Loading())
    val contactsList = _contactsList

    private val _favoriteContactsList = MutableStateFlow<List<Contact>>(emptyList())
    val favoriteContactsList = _favoriteContactsList


    init {
        getContactList()
        getFavoriteContactList()
    }

    private fun getContactList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllContact().collect { resource ->
                when (resource) {
                    is Resource.Error -> _contactsList.value = resource
                    is Resource.Loading -> _contactsList.value = resource
                    is Resource.Success -> _contactsList.value = resource
                }
            }
        }
    }

    private fun getFavoriteContactList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavoriteContacts().collect {
                _favoriteContactsList.value = it
            }
        }
    }


    fun deleteContact(phoneNumber: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
//                contactUseCase.deleteUseCase(context = context, phoneNumber = phoneNumber)
            }
        }
    }
}
