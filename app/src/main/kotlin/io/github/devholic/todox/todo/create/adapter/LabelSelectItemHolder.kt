package io.github.devholic.todox.todo.create.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import io.github.devholic.todox.db.TodoLabel
import kotlinx.android.synthetic.main.item_label.view.*

class LabelSelectItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(label: TodoLabel, selected: Boolean) {
        itemView.label.text = label.label
        itemView.isPressed = selected
    }
}