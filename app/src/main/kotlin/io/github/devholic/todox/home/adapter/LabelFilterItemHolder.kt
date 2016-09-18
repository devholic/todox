package io.github.devholic.todox.home.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import io.github.devholic.todox.db.TodoLabel
import kotlinx.android.synthetic.main.item_label_filter.view.*

class LabelFilterItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(label: TodoLabel) {
        itemView.label.text = label.label
    }
}