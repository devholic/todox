package io.github.devholic.todox.home.adapter

import android.view.View
import io.github.devholic.todox.base.BaseHolder
import io.github.devholic.todox.db.TodoLabel
import kotlinx.android.synthetic.main.item_label_filter.view.*

class LabelFilterItemHolder(itemView: View) : BaseHolder<TodoLabel>(itemView) {

    override fun bind(data: TodoLabel) {
        itemView.label.text = data.label
    }
}