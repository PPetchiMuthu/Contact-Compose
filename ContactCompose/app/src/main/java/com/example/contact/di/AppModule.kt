package com.example.contact.di

import com.example.contact.use_case.ContactUseCase
import com.example.contact.use_case.DeleteUseCase
import com.example.contact.use_case.GetContactsUseCase
import com.example.contact.use_case.InsertContactUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideContactUseCase(): ContactUseCase {
        return ContactUseCase(
            getContactsUseCase = GetContactsUseCase(),
            insertContactUseCase = InsertContactUseCase(),
            deleteUseCase = DeleteUseCase()
        )
    }
}