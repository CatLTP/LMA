package com.udemy.lma.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

@Database(entities = [Category::class,Course::class], version = 1)
abstract class CourseDatabase : RoomDatabase() {

    abstract fun categoryDAO() : CategoryDAO
    abstract fun courseDAO() : CourseDAO

    private object RoomCallback : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            initializeData()
        }

        private fun initializeData() {
            val courseDAO = instance?.courseDAO()
            val categoryDAO = instance?.categoryDAO()

            CoroutineScope(Dispatchers.IO).launch {
                val category1 = Category()
                category1.categoryName = "Front End"
                category1.categoryDescription = "Web Development Interface"

                val category2 = Category(
                    categoryName = "Back End",
                    categoryDescription =  "Web Development Database")

                categoryDAO?.insert(category1)
                categoryDAO?.insert(category2)

                val course1 = Course()
                course1.courseName = "HTML"
                course1.unitPrice = "100$"
                course1.categoryID = 1

                val course2 = Course(
                    courseName = "CSS",
                    unitPrice = "150$",
                    categoryID = 1)

                val course3 = Course(
                    courseName = "PHP",
                    unitPrice = "200$",
                    categoryID = 2)

                val course4 = Course(
                    courseName = "AJAX",
                    unitPrice = "300$",
                    categoryID = 2)

                courseDAO?.insert(course1)
                courseDAO?.insert(course2)
                courseDAO?.insert(course3)
                courseDAO?.insert(course4)
            }
        }
    }

    companion object {
        @Volatile
        private var instance : CourseDatabase? = null

        @Synchronized
        fun getInstance(context : Context): CourseDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                    CourseDatabase::class.java,"courses_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(RoomCallback)
                    .build()
            }
            return instance!!
        }
    }


}