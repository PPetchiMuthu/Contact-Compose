package com.example.contact.use_case

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.*
import android.provider.ContactsContract.Data
import com.example.contact.model.Contact

class InsertContactUseCase {
    operator fun invoke(context: Context, contact: Contact) {
        val contentResolver = context.contentResolver

        // Insert a new RawContact record
        val rawContactUri =
            contentResolver.insert(ContactsContract.RawContacts.CONTENT_URI, ContentValues())
        val rawContactId = ContentUris.parseId(rawContactUri ?: Uri.EMPTY)

        // Insert the contact name
        val nameValues = ContentValues().apply {
            put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
            put(StructuredName.DISPLAY_NAME, contact.name)
            put(Data.RAW_CONTACT_ID, rawContactId)
        }
        contentResolver.insert(Data.CONTENT_URI, nameValues)

        // Insert the phone number
        val phoneValues = ContentValues().apply {
            put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
            put(Phone.NUMBER, contact.phoneNumber)
            put(Phone.TYPE, Phone.TYPE_MOBILE)
            put(Data.RAW_CONTACT_ID, rawContactId)
        }
        contentResolver.insert(Data.CONTENT_URI, phoneValues)

        // Insert the email address
        val emailValues = ContentValues().apply {
            put(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE)
            put(Email.DATA, contact.emailAddress)
            put(Email.TYPE, Email.TYPE_WORK)
            put(Data.RAW_CONTACT_ID, rawContactId)
        }
        contentResolver.insert(Data.CONTENT_URI, emailValues)

        // Add the contact photo
        if (contact.photo != null) {
            val photoValues = ContentValues().apply {
                put(Data.MIMETYPE, Photo.CONTENT_ITEM_TYPE)
                put(Photo.PHOTO, contact.photo)
                put(Data.RAW_CONTACT_ID, rawContactId)
            }
            contentResolver.insert(Data.CONTENT_URI, photoValues)
        }

        // Add the favorite status
        val favoriteValues = ContentValues().apply {
            put(ContactsContract.Contacts.STARRED, if (contact.isFavorite) 1 else 0)
            put(ContactsContract.Contacts._ID, rawContactId)
        }
        contentResolver.update(ContactsContract.Contacts.CONTENT_URI, favoriteValues, null, null)

    }

//    operator fun invoke(context: Context, contact: Contact) {
//        val values = ContentValues()
//        val contentResolver = context.contentResolver
//
//        // Add the contact name
//        values.put(StructuredName.DISPLAY_NAME, contact.name)
//
//        // Add the contact phone number
//        val phone = ContentValues().apply {
//            put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
//            put(Phone.NUMBER, contact.phoneNumber)
//            put(Phone.TYPE, Phone.TYPE_MOBILE)
//        }
//        contentResolver.insert(ContactsContract.Data.CONTENT_URI, phone)
//
//        // Add the contact email
//        val email = ContentValues().apply {
//            put(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE)
//            put(Email.DATA, contact.emailAddress)
//            put(Email.TYPE, Email.TYPE_WORK)
//        }
//        contentResolver.insert(ContactsContract.Data.CONTENT_URI, email)
//
//        // Add the contact photo
//        if (contact.photo != null) {
//            values.put(Photo.PHOTO, contact.photo)
//        }
//
//        // Add the favorite status
//        values.put(ContactsContract.Contacts.STARRED, if (contact.isFavorite) 1 else 0)
//
//        // Insert the new contact
//        contentResolver.insert(ContactsContract.RawContacts.CONTENT_URI, values)
//
//
////        val values = ContentValues()
////        val contentResolver = context.contentResolver
////
////        // Add the contact name
////        values.put(StructuredName.DISPLAY_NAME, contact.name)
//////        values.put(Phone.NUMBER, contact.phoneNumber)
////
////        val phone = ContentValues().apply {
////            put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
////            put(Phone.NUMBER, contact.phoneNumber)
////            put(Phone.TYPE, Phone.TYPE_MOBILE)
////        }
////        values.put(Data.CONTENT_URI.toString(), phone.toString())
////
////        // Add the contact email
////        val email = ContentValues().apply {
////            put(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE)
////            put(Email.DATA, contact.emailAddress)
////            put(Email.TYPE, Email.TYPE_WORK)
////        }
////        values.put(Data.CONTENT_URI.toString(), email.toString())
////
////        // Add the contact photo
////        if (contact.photo != null) {
////            values.put(Photo.PHOTO, contact.photo)
////        }
////
////        // Add the favorite status
////        values.put(ContactsContract.Contacts.STARRED, if (contact.isFavorite) 1 else 0)
////
////        // Insert the new contact
////        contentResolver.insert(RawContacts.CONTENT_URI, values)
//
////        // Get the new contact ID
////        val newContactId = uri?.lastPathSegment
////
////        // Update the contact photo if necessary
////        if (contact.photo != null) {
////            if (newContactId != null) {
////                val value = ContentValues().apply {
////                    put(Data.IS_SUPER_PRIMARY, 1)
////                    put(Data.MIMETYPE, Photo.CONTENT_ITEM_TYPE)
////                    put(Photo.PHOTO, toByteArray(contact.photo))
////                }
////
////                val selection = Data.CONTACT_ID + " = ? AND " + Data.MIMETYPE + " = ?"
////                val selectionArgs = arrayOf(newContactId, Photo.CONTENT_ITEM_TYPE)
////                contentResolver.update(Data.CONTENT_URI, value, selection, selectionArgs)
////            }
////        }
//    }
//
//    private fun toByteArray(bitmap: Bitmap): ByteArray {
//        val stream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
//        return stream.toByteArray()
//    }
}