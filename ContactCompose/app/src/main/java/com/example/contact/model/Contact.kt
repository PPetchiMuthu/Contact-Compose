package com.example.contact.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class Contact(
    @PrimaryKey val id: String ,
    val name: String,
    val phoneNumber: String?,
    val emailAddress: String?,
    val photo: ByteArray? = null,
    val isFavorite: Boolean = false
)

