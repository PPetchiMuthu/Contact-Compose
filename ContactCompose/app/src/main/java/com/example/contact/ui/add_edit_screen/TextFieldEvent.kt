package com.example.contact.ui.add_edit_screen

import android.graphics.Bitmap

sealed class TextFieldEvent {
    data class EnteredName(val string: String) : TextFieldEvent()
    data class EnteredPhoneNumber(val string: String) : TextFieldEvent()
    data class EnteredEmail(val string: String) : TextFieldEvent()
    data class IsFavourite(val boolean:Boolean) : TextFieldEvent()
    data class PickedImage(val image: Bitmap) : TextFieldEvent()
    object SaveContact : TextFieldEvent()
}

data class TextFieldState(
    val text: String = "",
    val hint: String = ""
)