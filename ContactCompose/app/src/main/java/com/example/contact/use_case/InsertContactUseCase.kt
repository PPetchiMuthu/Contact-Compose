package com.example.contact.use_case

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.*
import android.provider.ContactsContract.Data
import android.provider.ContactsContract.RawContacts
import com.example.contact.model.Contact
import java.io.ByteArrayOutputStream

class InsertContactUseCase {
    operator fun invoke(context: Context, contact: Contact) {
        val values = ContentValues()
        val contentResolver = context.contentResolver

        // Add the contact name
        values.put(StructuredName.DISPLAY_NAME, contact.name)

        val phone = ContentValues().apply {
            put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
            put(Phone.NUMBER, contact.phoneNumber)
            put(Phone.TYPE, Phone.TYPE_MOBILE)
        }
        values.put(Data.CONTENT_URI.toString(), phone.toString())

        // Add the contact email
        val email = ContentValues().apply {
            put(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE)
            put(Email.DATA, contact.emailAddress)
            put(Email.TYPE, Email.TYPE_WORK)
        }
        values.put(Data.CONTENT_URI.toString(), email.toString())

        // Add the contact photo
        if (contact.photo != null) {
            values.put(Photo.PHOTO, toByteArray(contact.photo))
        }

        // Add the favorite status
        values.put(ContactsContract.Contacts.STARRED, if (contact.isFavorite) 1 else 0)

        // Insert the new contact
        contentResolver.insert(RawContacts.CONTENT_URI, values)

//        // Get the new contact ID
//        val newContactId = uri?.lastPathSegment
//
//        // Update the contact photo if necessary
//        if (contact.photo != null) {
//            if (newContactId != null) {
//                val value = ContentValues().apply {
//                    put(Data.IS_SUPER_PRIMARY, 1)
//                    put(Data.MIMETYPE, Photo.CONTENT_ITEM_TYPE)
//                    put(Photo.PHOTO, toByteArray(contact.photo))
//                }
//
//                val selection = Data.CONTACT_ID + " = ? AND " + Data.MIMETYPE + " = ?"
//                val selectionArgs = arrayOf(newContactId, Photo.CONTENT_ITEM_TYPE)
//                contentResolver.update(Data.CONTENT_URI, value, selection, selectionArgs)
//            }
//        }
    }

    private fun toByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }
}