package com.udemy.lma.model

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CourseShopRepository(
    application: Application
) {

    private var categoryDAO : CategoryDAO
    private var courseDAO : CourseDAO

    init {
        val courseDb = CourseDatabase.getInstance(application)
        categoryDAO = courseDb.categoryDAO()
        courseDAO = courseDb.courseDAO()
    }

    fun getCourses(categoryID : Int) : LiveData<List<Course>> {
        return courseDAO.getCoursesByID(categoryID)
    }

    fun getCategories() : LiveData<List<Category>> {
        return categoryDAO.getAllCategories()
    }

    suspend fun insertCategory(category : Category) {
        categoryDAO.insert(category)
    }

    suspend fun insertCourse(course : Course) {
        Log.i("ADD REPOSITORY", "ADDED NEW COURSE")
        courseDAO.insert(course)
    }

    suspend fun deleteCategory(category : Category) {
        categoryDAO.delete(category)
    }

    suspend fun deleteCourse(course : Course) {
        courseDAO.delete(course)
    }

    suspend fun updateCategory(category: Category) {
        categoryDAO.update(category)
    }

    suspend fun updateCourse(course: Course) {
        courseDAO.update(course)
    }
}