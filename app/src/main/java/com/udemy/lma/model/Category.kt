package com.udemy.lma.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "categories_table")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
): BaseObservable() {

    @ColumnInfo(name = "category_name")
    @get:Bindable
    var categoryName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.categoryName)
        }

    @ColumnInfo(name = "category_description")
    @get:Bindable
    var categoryDescription: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.categoryDescription)
        }

    constructor(categoryName: String, categoryDescription: String) : this(0) {
        this.categoryName = categoryName
        this.categoryDescription = categoryDescription
   }
    
   @Ignore
   constructor() : this(categoryName = "" , categoryDescription = "")

    override fun toString(): String {
        return categoryName
    }


}