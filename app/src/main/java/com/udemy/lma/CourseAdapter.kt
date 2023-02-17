package com.udemy.lma

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.udemy.lma.databinding.CourseListItemBinding
import com.udemy.lma.model.Course

class CourseAdapter : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(course : Course)
    }

    private lateinit var listener : OnItemClickListener

    fun setListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    var courses = ArrayList<Course>()
        set(value) {
            val result = DiffUtil.calculateDiff(
                CourseDiffCallback(courses, value),
                false)

            field = value
            result.dispatchUpdatesTo(CourseAdapter())
        }

    inner class CourseViewHolder(val courseListItemBinding : CourseListItemBinding)
        : RecyclerView.ViewHolder(courseListItemBinding.root) {

            init {
                courseListItemBinding.root.setOnClickListener {
                    val clickedPosition = adapterPosition

                    if (clickedPosition != RecyclerView.NO_POSITION) {
                        listener.onItemClick(courses[clickedPosition])
                    }
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val courseListItemBinding : CourseListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.course_list_item,
            parent,
            false
        )

        return CourseViewHolder(courseListItemBinding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = courses[position]
        holder.courseListItemBinding.course = course
    }

    override fun getItemCount(): Int {
        return courses.size
    }

}