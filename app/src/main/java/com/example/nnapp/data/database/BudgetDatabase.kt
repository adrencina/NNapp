package com.example.nnapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.nnapp.data.dao.BudgetDao
import com.example.nnapp.data.converter.MaterialConverter
import com.example.nnapp.data.entity.BudgetEntity

@Database(entities = [BudgetEntity::class], version = 1, exportSchema = false)
@TypeConverters(MaterialConverter::class)
abstract class BudgetDatabase : RoomDatabase() {
    abstract fun budgetDao(): BudgetDao
}