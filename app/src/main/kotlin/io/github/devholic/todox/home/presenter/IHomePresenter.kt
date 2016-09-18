package io.github.devholic.todox.home.presenter

import io.github.devholic.todox.db.Todo
import io.github.devholic.todox.db.TodoLabel
import io.github.devholic.todox.home.view.HomeView
import rx.Observable
import rx.Subscription

interface IHomePresenter {

    fun addSubscription(s: Subscription)
    fun clearSubscription()
    fun deleteTodo(todo: Todo)
    fun getLabelList(): Observable<List<TodoLabel>>
    fun getTodoList(): Observable<List<Todo>>
    fun onBackPressed(): Boolean
    fun recoverTodo()
    fun setQuerySubscription(s: Subscription)
    fun setView(view: HomeView)
}