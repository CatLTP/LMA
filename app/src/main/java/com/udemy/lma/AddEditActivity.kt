package com.udemy.lma

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.udemy.lma.databinding.ActivityAddEditBinding
import com.udemy.lma.model.Course

class AddEditActivity : AppCompatActivity() {

    private lateinit var course: Course
    object StaticVariable {
        const val COURSE_ID = "courseID"
        const val COURSE_NAME = "courseName"
        const val UNIT_PRICE = "unitPrice"
    }

    private lateinit var activityAddEditBinding : ActivityAddEditBinding
    private lateinit var clickHandlers: AddAndEditActivityClickHandlers

    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        course = Course()
        activityAddEditBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_add_edit)
        activityAddEditBinding.course = course

        clickHandlers = AddAndEditActivityClickHandlers(this)
        activityAddEditBinding.clickHandler = clickHandlers

        val i = intent
        if (i.hasExtra(StaticVariable.COURSE_ID)) {
            title = "Edit Course"
            course.courseName = i.getStringExtra(StaticVariable.COURSE_NAME)
            course.unitPrice = i.getStringExtra(StaticVariable.UNIT_PRICE)
        } else {
            title = "Create New Course"
        }
    }


    inner class AddAndEditActivityClickHandlers(private val context : Context) {

        fun onSubmitButtonClicked(view : View) {
            if (course.courseName == null) {
                Toast.makeText(context,"Course name is empty !" , Toast.LENGTH_SHORT).show()
            }
            else {
                val i = Intent()
                i.putExtra(StaticVariable.COURSE_NAME,course.courseName)
                i.putExtra(StaticVariable.UNIT_PRICE,course.unitPrice)
                setResult(RESULT_OK,i)
                finish()
            }
        }

    }


}