package com.example.nnapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    @Provides
//    @Singleton
//    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()
//
//    @Provides
//    @Singleton
//    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()
//
//    @Provides
//    @Singleton
//    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()
}