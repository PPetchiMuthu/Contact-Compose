package com.example.contact.use_case

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.provider.ContactsContract.Contacts
import com.example.contact.model.Contact
import java.io.ByteArrayOutputStream

class GetContactsUseCase {
    operator fun invoke(context: Context): List<Contact> {
        return getContacts(context)
    }

    @SuppressLint("Range")
    private fun getContacts(context: Context): List<Contact> {
        val contactList = mutableListOf<Contact>()
        val fields = arrayOf(
            Contacts._ID,
            Contacts.DISPLAY_NAME,
            Contacts.STARRED
        )
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(
            Contacts.CONTENT_URI,
            fields,
            Contacts.HAS_PHONE_NUMBER + " = 1",
            null,
            Contacts.DISPLAY_NAME
        )
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val contactId =
                    cursor.getString(cursor.getColumnIndex(Contacts._ID))
                val name =
                    cursor.getString(cursor.getColumnIndex(Contacts.DISPLAY_NAME))
                val isFavorite =
                    cursor.getInt(cursor.getColumnIndex(Contacts.STARRED)) == 1

                // Get the phone number
                val phoneCursor = contentResolver.query(
                    Phone.CONTENT_URI,
                    arrayOf(Phone.NUMBER),
                    Phone.CONTACT_ID + " = ?",
                    arrayOf(contactId),
                    null
                )

                var phoneNumber: String? = null
                if (phoneCursor != null && phoneCursor.moveToFirst()) {
                    phoneNumber =
                        phoneCursor.getString(phoneCursor.getColumnIndex(Phone.NUMBER))
                    phoneCursor.close()
                }

                // Get the email address
                val emailCursor = contentResolver.query(
                    Email.CONTENT_URI,
                    arrayOf(Email.DATA),
                    Email.CONTACT_ID + " = ?",
                    arrayOf(contactId),
                    null
                )

                var emailAddress: String? = null
                if (emailCursor != null && emailCursor.moveToFirst()) {
                    emailAddress =
                        emailCursor.getString(emailCursor.getColumnIndex(Email.DATA))
                    emailCursor.close()
                }

                // Get the contact photo
                val photoUri = ContentUris.withAppendedId(
                    Contacts.CONTENT_URI,
                    contactId.toLong()
                )
                val inputStream = Contacts.openContactPhotoInputStream(
                    contentResolver,
                    photoUri,
                    true
                )
                val bitmap = if (inputStream != null) {
                    BitmapFactory.decodeStream(inputStream)
                } else {
                    null
                }
                val outputStream = ByteArrayOutputStream()
                bitmap?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                val photoBytes = outputStream.toByteArray()

                // Add the contact to the list
                contactList.add(
                    Contact(
                        contactId,
                        name,
                        phoneNumber,
                        emailAddress,
                        photoBytes,
                        isFavorite
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return contactList
    }
}