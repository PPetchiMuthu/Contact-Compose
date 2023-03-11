package com.example.contact.ui.add_edit_screen

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contact.model.Contact
import com.example.contact.repository.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val repository: ContactRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _contactName = mutableStateOf(
        TextFieldState(
            hint = "Name"
        )
    )
    val contactName: State<TextFieldState> = _contactName

    private val _contactNumber = mutableStateOf(
        TextFieldState(
            hint = "Phone Number"
        )
    )
    val contactNumber: State<TextFieldState> = _contactNumber

    private val _contactEmail = mutableStateOf(
        TextFieldState(
            hint = "Email Id"
        )
    )
    val contactEmail: State<TextFieldState> = _contactEmail

    private val _isFavourite = mutableStateOf(false)
    val isFavourite = _isFavourite

    private val _contactImage = mutableStateOf<ByteArray?>(null)
    val contactImage = _contactImage

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentContactId: String? = null

    init {
        savedStateHandle.get<String>("contactId")?.let { contactId ->
            if (contactId != "") {
                viewModelScope.launch {
                    repository.getContact(contactId).also { contact ->
                        currentContactId = contact.id
                        _contactName.value = contactName.value.copy(
                            text = contact.name
                        )
                        _contactNumber.value = contactNumber.value.copy(
                            text = contact.phoneNumber ?: ""
                        )
                        _contactEmail.value = contactEmail.value.copy(
                            text = contact.emailAddress ?: ""
                        )
                        _contactImage.value = contact.photo
                        _isFavourite.value = contact.isFavorite
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditEvent) {
        when (event) {
            is AddEditEvent.EnteredEmail -> {
                _contactEmail.value = contactEmail.value.copy(
                    text = event.string
                )
            }
            is AddEditEvent.EnteredName -> {
                _contactName.value = contactName.value.copy(
                    text = event.string
                )
            }
            is AddEditEvent.EnteredPhoneNumber -> {
                _contactNumber.value = contactNumber.value.copy(
                    text = event.string
                )
            }
            AddEditEvent.IsFavourite -> {
                _isFavourite.value = !isFavourite.value
            }
            is AddEditEvent.PickedImage -> {
                _contactImage.value = uriToByteArray(event.uri, event.context)
            }
            AddEditEvent.SaveContact -> {
                viewModelScope.launch {
                    try {
                        val contact = Contact(
                            "----",
                            _contactName.value.text,
                            _contactNumber.value.text,
                            _contactEmail.value.text,
                            if (_contactImage.value != null) _contactImage.value
                            else null,
                            isFavourite.value
                        )
                        repository.insertContact(contact)
                        _eventFlow.emit(UiEvent.SaveContact)
                    } catch (e: Exception) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                message = "Couldn't add Contact"
                            )
                        )
                    }
                }
            }
        }
    }

    fun uriToByteArray(uri: Uri, context: Context): ByteArray? {
        var byteArray: ByteArray? = null
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            ByteArrayOutputStream().use { output ->
                inputStream.copyTo(output)
                byteArray = output.toByteArray()
            }
        }
        return byteArray
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        object SaveContact : UiEvent()
    }
}