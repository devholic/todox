package io.github.devholic.todox.todo.create.presenter

import io.github.devholic.todox.db.Todo
import io.github.devholic.todox.db.TodoLabel
import io.github.devholic.todox.todo.create.view.TodoCreateView
import rx.Observable
import rx.Subscription
import java.util.*

interface ITodoCreatePresenter {

    fun addSubscription(s: Subscription)
    fun clearSubscription()
    fun getLabelList(): Observable<List<TodoLabel>>
    fun onLabelSelected(selected: ArrayList<Int>)
    fun onPriorityChanged(priority: Int)
    fun onTodoChanged(todo: String)
    fun saveTodo(): Todo?
    fun setTodo(todo: Todo)
    fun setView(view: TodoCreateView)
    fun showLabelSelectDialog()
    fun updateLabelList(list: List<TodoLabel>)
}