package com.example.nnapp.di

import android.content.Context
import androidx.room.Room
import com.example.nnapp.data.dao.BudgetDao
import com.example.nnapp.data.database.BudgetDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo para proveer las dependencias de la base de datos Room.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideBudgetDatabase(@ApplicationContext context: Context): BudgetDatabase {
        return Room.databaseBuilder(context, BudgetDatabase::class.java, "budget_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideBudgetDao(database: BudgetDatabase): BudgetDao {
        return database.budgetDao()
    }
}