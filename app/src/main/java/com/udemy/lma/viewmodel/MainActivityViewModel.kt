package com.udemy.lma.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.udemy.lma.model.Category
import com.udemy.lma.model.Course
import com.udemy.lma.model.CourseShopRepository
import kotlinx.coroutines.launch

class MainActivityViewModel(
    application: Application) : AndroidViewModel(application) {

    // Repository
    private var repository : CourseShopRepository
    init {
        repository = CourseShopRepository(application)
    }

    // Live Data
    private lateinit var allCategories : LiveData<List<Category>>
    private lateinit var coursesOfSelectedCategory : LiveData<List<Course>>

    fun getAllCategories() : LiveData<List<Category>> {
        allCategories = repository.getCategories()
        return allCategories
    }

    fun getCoursesOfSelectedCategory(categoryID: Int) : LiveData<List<Course>> {
        coursesOfSelectedCategory = repository.getCourses(categoryID)
        return coursesOfSelectedCategory
    }

    fun addNewCourse(course : Course)  =
        viewModelScope.launch {
            Log.i("ADD VIEW MODEL", "ADDED NEW COURSE")
            repository.insertCourse(course)
        }

    fun updateCourse(course : Course)  =
        viewModelScope.launch {
            repository.updateCourse(course)
        }

    fun deleteCourse(course : Course)  =
        viewModelScope.launch {
            repository.deleteCourse(course)
        }
}