package io.github.devholic.todox.home.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.devholic.todox.R
import io.github.devholic.todox.db.TodoLabel
import rx.Observable
import rx.subjects.PublishSubject
import java.util.*

class LabelFilterAdapter : RecyclerView.Adapter<LabelFilterItemHolder> {

    private val clickSubject: PublishSubject<TodoLabel> by lazy { PublishSubject.create<TodoLabel>() }

    private var data: List<TodoLabel> = Collections.emptyList()

    constructor(data: List<TodoLabel>) {
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabelFilterItemHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_label_filter, parent, false)

        return LabelFilterItemHolder(v)
    }

    override fun onBindViewHolder(holder: LabelFilterItemHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener({ clickSubject.onNext(data[position]) })
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun getClickEventObservable(): Observable<TodoLabel> {
        return clickSubject.asObservable()
    }
}
