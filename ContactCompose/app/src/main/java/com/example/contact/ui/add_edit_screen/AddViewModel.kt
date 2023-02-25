package com.example.contact.ui.add_edit_screen

import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contact.model.Contact
import com.example.contact.use_case.ContactUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val contactUseCase: ContactUseCase
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

    private val _contactImage = mutableStateOf<Bitmap?>(null)
    val contactImage = _contactImage

    fun onEvent(event: TextFieldEvent) {
        when (event) {
            is TextFieldEvent.EnteredEmail -> {
                _contactEmail.value = contactEmail.value.copy(
                    text = event.string
                )
            }
            is TextFieldEvent.EnteredName -> {
                _contactName.value = contactName.value.copy(
                    text = event.string
                )
            }
            is TextFieldEvent.EnteredPhoneNumber -> {
                _contactNumber.value = contactNumber.value.copy(
                    text = event.string
                )
            }
            is TextFieldEvent.IsFavourite -> {
                _isFavourite.value = !isFavourite.value
            }
            is TextFieldEvent.PickedImage -> {
                _contactImage.value = event.image
            }
            TextFieldEvent.SaveContact -> {
                viewModelScope.launch {
                    val contact = Contact(
                        "1",
                        _contactName.value.text,
                        _contactNumber.value.text,
                        _contactEmail.value.text,
                        _contactImage.value,
                        isFavourite.value
                    )
                }
            }
        }
    }
}