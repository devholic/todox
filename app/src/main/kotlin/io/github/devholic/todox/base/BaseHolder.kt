package io.github.devholic.todox.base

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(data: T)
}