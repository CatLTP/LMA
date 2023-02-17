package com.udemy.lma

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.udemy.lma.CourseAdapter.OnItemClickListener
import com.udemy.lma.databinding.ActivityMainBinding
import com.udemy.lma.model.Category
import com.udemy.lma.model.Course
import com.udemy.lma.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var categoriesList : ArrayList<Category>
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var handlers: MainActivityClickHandlers
    private lateinit var selectedCategory: Category

    //Recycler View
    private lateinit var courseRecyclerView : RecyclerView
    private lateinit var courseAdapter: CourseAdapter
    private lateinit var coursesList : ArrayList<Course>
    private var selectedCourseID = 0

    private lateinit var i : Intent
    private lateinit var addCourseLauncher : ActivityResultLauncher<Intent>
    private lateinit var editCourseLauncher : ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActivityViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        val categoryObserver = Observer<List<Category>> { categories ->
            categoriesList = categories as ArrayList<Category>

            for (c in categories) {
                c.categoryName.let { Log.i("TAG", it) }
            }

            showOnSpinner()
        }
        mainActivityViewModel.getAllCategories().observe(this, categoryObserver)

        handlers = MainActivityClickHandlers()
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        activityMainBinding.clickHandler = handlers

        i = Intent(this@MainActivity,AddEditActivity::class.java)
        registerActivityResult()
    }

    private fun registerActivityResult() {

        addCourseLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val intent = result.data

                val selectedCategoryID = selectedCategory.id
                val course = Course()
                course.categoryID = selectedCategoryID
                course.courseName = intent?.getStringExtra(AddEditActivity.StaticVariable.COURSE_NAME)
                course.unitPrice = intent?.getStringExtra(AddEditActivity.StaticVariable.UNIT_PRICE)
                if (course.courseName != null)
                    Log.i("COURSE INFO", course.courseName!!)

                mainActivityViewModel.addNewCourse(course)
            }
        }

        editCourseLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val intent = result.data

                val selectedCategoryID = selectedCategory.id
                val editCourse = Course()

                editCourse.categoryID = selectedCategoryID
                editCourse.courseName = intent?.getStringExtra(AddEditActivity.StaticVariable.COURSE_NAME)
                editCourse.unitPrice = intent?.getStringExtra(AddEditActivity.StaticVariable.UNIT_PRICE)
                editCourse.courseId = selectedCourseID

                mainActivityViewModel.updateCourse(editCourse)
            }
        }
    }

    private fun showOnSpinner() {
        val categoryArrayAdapter = ArrayAdapter(
            this,
            R.layout.spinner_item,
            categoriesList)

        categoryArrayAdapter.setDropDownViewResource(R.layout.spinner_item)
        activityMainBinding.spinnerAdapter = categoryArrayAdapter
    }

    fun loadCoursesArrayList(categoryID : Int) {
        mainActivityViewModel.getCoursesOfSelectedCategory(categoryID).observe(
            this
        ) { courses ->
            coursesList = courses as ArrayList<Course>
            loadRecyclerView()
        }
    }

    private fun loadRecyclerView() {
        courseRecyclerView = activityMainBinding.secondaryLayout.recyclerView
        courseRecyclerView.layoutManager = LinearLayoutManager(this)
        courseRecyclerView.setHasFixedSize(true)

        courseAdapter = CourseAdapter()
        courseRecyclerView.adapter = courseAdapter

        courseAdapter.courses = coursesList

        courseAdapter.setListener(object : OnItemClickListener {
            override fun onItemClick(course: Course) {

                selectedCourseID = course.courseId

                i.putExtra(AddEditActivity.StaticVariable.COURSE_ID, selectedCourseID)
                i.putExtra(AddEditActivity.StaticVariable.COURSE_NAME, course.courseName)
                i.putExtra(AddEditActivity.StaticVariable.UNIT_PRICE, course.unitPrice)

                editCourseLauncher.launch(i)
            }
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val courseToDelete = coursesList[viewHolder.adapterPosition]
                mainActivityViewModel.deleteCourse(courseToDelete)
            }
        }).attachToRecyclerView(courseRecyclerView)
    }

    inner class MainActivityClickHandlers{

        fun onFABClicked(view : View) {
            addCourseLauncher.launch(i)
        }

        fun onSelectedItem(parent : AdapterView<*>, view : View, pos : Int, id : Long ) {
            selectedCategory = parent.getItemAtPosition(pos) as Category

            val message = "id is : " + selectedCategory.id +
                        "\n name is " + selectedCategory.categoryName

            Toast.makeText(parent.context,message,Toast.LENGTH_LONG).show()

            loadCoursesArrayList(selectedCategory.id)
        }

    }
}