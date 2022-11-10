package com.codedev.pharmacy.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import com.codedev.pharmacy.util.Constants.SPLASH_SP
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore() = Firebase.firestore

    @Provides
    fun provideSharedPreferences(
        application: Application
    ) = application.getSharedPreferences(SPLASH_SP,MODE_PRIVATE)


}