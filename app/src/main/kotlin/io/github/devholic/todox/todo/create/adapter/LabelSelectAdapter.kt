package io.github.devholic.todox.todo.create.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.devholic.todox.R
import io.github.devholic.todox.db.TodoLabel
import java.util.*

class LabelSelectAdapter : RecyclerView.Adapter<LabelSelectItemHolder> {

    private var data: List<TodoLabel> = Collections.emptyList()
    var selected: ArrayList<Int> = ArrayList()
        get

    constructor(data: List<TodoLabel>, selected: ArrayList<Int>) {
        this.data = data
        this.selected = selected
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabelSelectItemHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_label, parent, false)

        return LabelSelectItemHolder(v)
    }

    override fun onBindViewHolder(holder: LabelSelectItemHolder, position: Int) {
        holder.bind(data[position], isSelected(data[position].id))
        holder.itemView.setOnClickListener({ onItemSelected(data[position].id) })
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private fun onItemSelected(id: Int) {

        val idx = selected.indexOf(id)

        if (idx == -1) {
            selected.add(id)
        } else {
            selected.removeAt(idx)
        }
        notifyDataSetChanged()
    }

    private fun isSelected(id: Int): Boolean {

        val idx = selected.indexOf(id)

        return idx != -1
    }
}