package com.example.contact.ui.all_contact_screen

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.contact.model.Contact
import com.example.contact.use_case.ContactUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AllContactScreenViewModel @Inject constructor(
    private val contactUseCase: ContactUseCase, application: Application
) : AndroidViewModel(application) {

    private var _contactsList = mutableStateListOf<Contact>()
    val contactsList = _contactsList

    init {
        getContactList(application.applicationContext)
    }

    private fun getContactList(context: Context) {
        viewModelScope.launch {
            val list = withContext(Dispatchers.IO) {
                contactUseCase.getContactsUseCase(
                    context
                )
            }
            _contactsList.addAll(list) // Update the value of _contactsList
        }
    }

    fun deleteContact(context: Context, phoneNumber: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                contactUseCase.deleteUseCase(context = context, phoneNumber = phoneNumber)
            }
        }
    }
}
