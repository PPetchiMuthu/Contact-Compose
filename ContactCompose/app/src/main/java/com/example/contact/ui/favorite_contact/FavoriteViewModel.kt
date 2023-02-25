package com.example.contact.ui.favorite_contact

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
class FavoriteViewModel @Inject constructor(
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
                contactUseCase.getContactsUseCase(context)
            }
            val filteredList = list.filter { it.isFavorite }
            _contactsList.addAll(filteredList)
        }
    }
}