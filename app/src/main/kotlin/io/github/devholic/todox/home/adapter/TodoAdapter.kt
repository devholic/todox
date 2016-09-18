package io.github.devholic.todox.home.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.devholic.todox.R
import io.github.devholic.todox.db.Todo
import io.github.devholic.todox.db.TodoLabel
import kotlinx.android.synthetic.main.item_todo.view.*
import rx.Observable
import rx.functions.Action1
import rx.subjects.PublishSubject
import java.util.*

class TodoAdapter : RecyclerView.Adapter<TodoItemHolder>(), Action1<List<Todo>> {

    private val checkSubject: PublishSubject<Todo> by lazy { PublishSubject.create<Todo>() }
    private val clickSubject: PublishSubject<Todo> by lazy { PublishSubject.create<Todo>() }

    private var data: List<Todo> = Collections.emptyList()
    private var label: HashMap<Int, String> = HashMap()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)

        return TodoItemHolder(v)
    }

    override fun onBindViewHolder(holder: TodoItemHolder, position: Int) {
        holder.bind(data[position], getLabelString(data[position].labelId))
        holder.itemView.checkbox.setOnCheckedChangeListener({ compoundButton, b ->
            if (b) {
                checkSubject.onNext(data[position])
            }
        })
        holder.itemView.setOnClickListener({
            clickSubject.onNext(data[position])
        })
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun call(data: List<Todo>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun setLabelList(labelList: List<TodoLabel>) {
        this.label.clear()
        labelList.forEach { this.label.put(it.id, it.label) }
        notifyDataSetChanged()
    }

    fun getCheckboxEventObservable(): Observable<Todo> {
        return checkSubject.asObservable()
    }

    fun getClickEventObservable(): Observable<Todo> {
        return clickSubject.asObservable()
    }

    private fun getLabelString(labelId: String): String {

        val builder = StringBuilder()
        val idList = Todo.parseLabelId(labelId)

        idList.forEach {
            builder.append(" #${label[it]}")
        }
        return builder.toString()
    }
}