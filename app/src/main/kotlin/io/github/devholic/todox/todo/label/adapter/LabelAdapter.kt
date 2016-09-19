package io.github.devholic.todox.todo.label.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.devholic.todox.R
import io.github.devholic.todox.db.TodoLabel
import rx.Observable
import rx.functions.Action1
import rx.subjects.PublishSubject
import java.util.*

class LabelAdapter : RecyclerView.Adapter<LabelItemHolder>(), Action1<List<TodoLabel>> {

    private val clickSubject: PublishSubject<TodoLabel> by lazy { PublishSubject.create<TodoLabel>() }

    private var data: List<TodoLabel> = Collections.emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabelItemHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_label, parent, false)

        return LabelItemHolder(v)
    }

    override fun onBindViewHolder(holder: LabelItemHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener({ clickSubject.onNext(data[position]) })
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun call(data: List<TodoLabel>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun getClickEventObservable(): Observable<TodoLabel> {
        return clickSubject.asObservable()
    }
}