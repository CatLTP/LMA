package com.udemy.lma.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE

@Entity(tableName = "course_table", foreignKeys = [ForeignKey(
        entity = Category::class,
        parentColumns = ["id"],
        childColumns = ["category_id"],
        onDelete = CASCADE)]
)
data class Course(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "course_id")
    var courseId: Int = 0
) : BaseObservable()  {

    @ColumnInfo(name = "course_name")
    @get:Bindable
    var courseName: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.courseName)
        }

    @ColumnInfo(name = "unit_price")
    @get:Bindable
    var unitPrice: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.unitPrice)
        }

    @ColumnInfo(name = "category_id")
    @get:Bindable
    var categoryID: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.categoryID)
        }

    constructor(courseName: String?, unitPrice: String?, categoryID : Int) : this(0) {
        this.courseName = courseName
        this.unitPrice = unitPrice
        this.categoryID = categoryID
    }

    @Ignore
    constructor() : this(courseName = "" , unitPrice = "", categoryID = 0)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false

        val course = other as Course
        return  courseId == course.courseId &&
                categoryID == course.categoryID &&
                unitPrice == course.unitPrice &&
                courseName == course.courseName
    }

    override fun hashCode(): Int {
        var result = courseId
        result = 31 * result + (courseName?.hashCode() ?: 0)
        result = 31 * result + (unitPrice?.hashCode() ?: 0)
        result = 31 * result + categoryID
        return result
    }

}