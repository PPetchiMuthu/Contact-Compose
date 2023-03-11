package com.example.contact.di

import android.app.Application
import androidx.room.Room
import com.example.contact.db.ContactDatabase
import com.example.contact.repository.ContactRepository
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

    @Provides
    @Singleton
    fun provideContactDatabase(app: Application): ContactDatabase {
        return Room.databaseBuilder(
            app,
            ContactDatabase::class.java,
            "contact_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideContactRepository(
        db: ContactDatabase,
        useCase: ContactUseCase,
        app: Application
    ): ContactRepository {
        return ContactRepository(db.contactDao, useCase, app)
    }


}