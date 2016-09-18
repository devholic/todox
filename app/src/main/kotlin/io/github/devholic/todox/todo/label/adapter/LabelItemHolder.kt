package io.github.devholic.todox.todo.label.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import io.github.devholic.todox.db.TodoLabel
import kotlinx.android.synthetic.main.item_label.view.*

class LabelItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(label: TodoLabel) {
        itemView.label.text = label.label
    }
}