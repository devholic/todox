package io.github.devholic.todox.todo.label.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.item_label.view.*

class LabelItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindLabel(label: String) {
        itemView.label.text = label
    }
}