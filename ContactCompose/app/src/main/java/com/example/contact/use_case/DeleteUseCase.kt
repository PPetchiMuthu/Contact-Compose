package com.example.contact.use_case

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log

class DeleteUseCase {
    @SuppressLint("Range")
    operator fun invoke(context: Context, phoneNumber: String) {

        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null
        )
        if ((cursor?.count ?: 0) > 0) {
            while (cursor?.moveToNext() == true) {
                val contactId =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val contactName =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                if (Integer.parseInt(
                        cursor.getString(
                            cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
                        )
                    ) > 0
                ) {
                    val phoneCursor = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(contactId),
                        null
                    )
                    while (phoneCursor?.moveToNext() == true) {
                        val contactPhoneNumber =
                            phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        if (contactPhoneNumber == phoneNumber) {
                            val uri = Uri.withAppendedPath(
                                ContactsContract.Contacts.CONTENT_URI,
                                contactId
                            )
                            contentResolver.delete(uri, null, null)
                            Log.i("Contacts", "Deleted contact $contactName")
                            return
                        }
                    }
                    phoneCursor?.close()
                }
            }
        }
        cursor?.close()
    }
}