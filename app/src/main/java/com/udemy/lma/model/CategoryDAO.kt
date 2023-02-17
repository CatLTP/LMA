package com.udemy.lma.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CategoryDAO {

    @Insert
    suspend fun insert(category : Category)

    @Update
    suspend fun update(category : Category)

    @Delete
    suspend fun delete(category: Category)

    @Query("SELECT * FROM categories_table")
    fun getAllCategories() : LiveData<List<Category>>
}