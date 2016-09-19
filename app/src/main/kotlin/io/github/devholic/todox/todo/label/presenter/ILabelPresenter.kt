package io.github.devholic.todox.todo.label.presenter

import io.github.devholic.todox.base.BasePresenter
import io.github.devholic.todox.db.TodoLabel
import rx.Observable

interface ILabelPresenter : BasePresenter {

    fun getLabelListObservable(): Observable<List<TodoLabel>>
    fun saveLabel(id: Int, label: String)
}