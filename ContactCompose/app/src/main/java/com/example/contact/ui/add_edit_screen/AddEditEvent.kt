package com.example.contact.ui.add_edit_screen

import android.content.Context
import android.net.Uri

sealed class AddEditEvent {
    data class EnteredName(val string: String) : AddEditEvent()
    data class EnteredPhoneNumber(val string: String) : AddEditEvent()
    data class EnteredEmail(val string: String) : AddEditEvent()
    data class PickedImage(val uri: Uri, val context: Context) : AddEditEvent()
    object IsFavourite : AddEditEvent()
    object SaveContact : AddEditEvent()
}

data class TextFieldState(
    val text: String = "",
    val hint: String = ""
)