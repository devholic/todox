package io.github.devholic.todox.todo.create.presenter

import io.github.devholic.todox.base.BasePresenter
import io.github.devholic.todox.db.Todo
import io.github.devholic.todox.db.TodoLabel
import io.github.devholic.todox.todo.create.view.TodoCreateView
import rx.Observable
import java.util.*

interface ITodoCreatePresenter : BasePresenter {

    fun getLabelListObservable(): Observable<List<TodoLabel>>
    fun onLabelSelected(selected: ArrayList<Int>)
    fun onPriorityChanged(priority: Int)
    fun onTodoChanged(todo: String)
    fun saveTodo(): Todo?
    fun setTodo(todo: Todo)
    fun setView(view: TodoCreateView)
    fun showLabelSelectDialog()
    fun updateLabelList(list: List<TodoLabel>)
}