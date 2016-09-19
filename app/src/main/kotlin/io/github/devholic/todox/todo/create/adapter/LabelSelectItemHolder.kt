package io.github.devholic.todox.todo.create.adapter

import android.view.View
import io.github.devholic.todox.base.BaseHolder
import io.github.devholic.todox.db.TodoLabel
import kotlinx.android.synthetic.main.item_label.view.*

class LabelSelectItemHolder(itemView: View) : BaseHolder<TodoLabel>(itemView) {

    override fun bind(data: TodoLabel) {
        itemView.label.text = data.label
    }

    fun bind(data: TodoLabel, selected: Boolean) {
        itemView.isPressed = selected
        bind(data)
    }
}