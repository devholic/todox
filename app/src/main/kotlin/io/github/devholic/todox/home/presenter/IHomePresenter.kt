package io.github.devholic.todox.home.presenter

import io.github.devholic.todox.db.Todo
import io.github.devholic.todox.db.TodoLabel
import io.github.devholic.todox.home.view.HomeView
import rx.Observable
import rx.Subscription
import java.util.*

interface IHomePresenter {

    fun addSubscription(s: Subscription)
    fun clearSubscription()
    fun deleteTodo(todo: Todo)
    fun getLabelList(): List<TodoLabel>
    fun getLabelListObservable(): Observable<List<TodoLabel>>
    fun getTodoListObservable(): Observable<List<Todo>>
    fun onBackPressed(): Boolean
    fun setLabelFilter(id: Int)
    fun setLabelList(label: List<TodoLabel>)
    fun setLabelMap(label: HashMap<Int, String>)
    fun setQuerySubscription(s: Subscription)
    fun setView(view: HomeView)
    fun undoDelete()
}