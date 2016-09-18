package io.github.devholic.todox.home.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import io.github.devholic.todox.R
import io.github.devholic.todox.db.Todo
import kotlinx.android.synthetic.main.item_todo.view.*

class TodoItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(data: Todo, label: String?) {

        val color: Int

        when (data.priority) {
            1 -> color = ContextCompat.getColor(itemView.context, R.color.priority1)
            2 -> color = ContextCompat.getColor(itemView.context, R.color.priority2)
            3 -> color = ContextCompat.getColor(itemView.context, R.color.priority3)
            else -> color = ContextCompat.getColor(itemView.context, R.color.priority4)
        }
        itemView.priority_indicator.setBackgroundColor(color)
        itemView.checkbox.isChecked = false
        itemView.todo.text = data.todo
        itemView.label.text = "${itemView.context.getString(R.string.home_priority)}${data.priority}$label"
    }
}