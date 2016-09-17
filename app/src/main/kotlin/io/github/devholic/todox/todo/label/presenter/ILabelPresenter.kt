package io.github.devholic.todox.todo.label.presenter

import io.github.devholic.todox.db.TodoLabel
import rx.Observable
import rx.Subscription

interface ILabelPresenter {

    fun addSubscription(s: Subscription)
    fun clearSubscription()
    fun getLabelList(): Observable<List<TodoLabel>>
    fun saveLabel(id: Int, label: String)
}