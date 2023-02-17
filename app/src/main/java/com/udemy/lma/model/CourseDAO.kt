package com.udemy.lma.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CourseDAO {

    @Insert
    suspend fun insert(course: Course)

    @Update
    suspend fun update(course: Course)

    @Delete
    suspend fun delete(course: Course)

    @Query("SELECT * FROM course_table")
    fun getAllCourses() : LiveData<List<Course>>

    @Query("SELECT * FROM course_table WHERE category_id==:categoryID")
    fun getCoursesByID(categoryID : Int) : LiveData<List<Course>>
}