package com.example.contact.model

import android.graphics.Bitmap

data class Contact(
    val id: String,
    val name: String,
    val phoneNumber: String?,
    val emailAddress: String?,
    val photo: Bitmap?,
    val isFavorite: Boolean
)

