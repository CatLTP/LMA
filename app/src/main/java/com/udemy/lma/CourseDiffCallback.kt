package com.udemy.lma

import androidx.recyclerview.widget.DiffUtil
import com.udemy.lma.model.Course

class CourseDiffCallback(
    private var oldCourses : ArrayList<Course>,
    private var newCourses : ArrayList<Course>
) : DiffUtil.Callback() {


    override fun getOldListSize(): Int {
        return oldCourses.size
    }

    override fun getNewListSize(): Int {
        return newCourses.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCourses[oldItemPosition].courseId == newCourses[newItemPosition].courseId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCourses[oldItemPosition] == newCourses[newItemPosition]
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}