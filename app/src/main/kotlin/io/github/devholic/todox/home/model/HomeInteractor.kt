package io.github.devholic.todox.home.model

import com.squareup.sqlbrite.BriteDatabase
import io.github.devholic.todox.db.Todo
import io.github.devholic.todox.db.TodoLabel
import rx.Observable
import javax.inject.Inject

class HomeInteractor @Inject constructor(val db: BriteDatabase) {

    private val labelQuery = "SELECT * FROM ${TodoLabel.TABLE}"
    private val todoQuery = "SELECT * FROM ${Todo.TABLE} ORDER BY ${Todo.PRIORITY} ASC"

    fun deleteTodo(id: Int) {
        db.delete(Todo.TABLE, "${Todo.ID}=?", Integer.toString(id))
    }

    fun getLabelList(): Observable<List<TodoLabel>> {
        return db.createQuery(TodoLabel.TABLE, labelQuery)
                .mapToList { TodoLabel.fromCursor(it) }
    }

    fun getTodoList(): Observable<List<Todo>> {
        return db.createQuery(Todo.TABLE, todoQuery)
                .mapToList { Todo.fromCursor(it) }
    }

    fun recoverTodo(todo: Todo) {
        db.insert(Todo.TABLE, todo.toContentValue())
    }
}