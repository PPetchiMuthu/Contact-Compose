package com.example.contact.use_case

data class ContactUseCase(
    val getContactsUseCase: GetContactsUseCase,
    val insertContactUseCase: InsertContactUseCase,
    val deleteUseCase: DeleteUseCase
)
