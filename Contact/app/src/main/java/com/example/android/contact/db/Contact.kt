package com.example.android.contact.db

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "contact_details")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val contactId: Int = 1,
    val name: String?,
    val phoneNum1: String?,
    val phoneNum2: String?,
    val phoneNum3: String?,
    val emailId: String?,
    val dob: String?,
    val favorite: Int,
    val groupContact: String?,
    val image : Bitmap
) : Parcelable
