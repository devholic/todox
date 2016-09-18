package io.github.devholic.todox.todo.create.model

import android.content.Context
import com.squareup.sqlbrite.BriteDatabase
import io.github.devholic.todox.R
import io.github.devholic.todox.db.Todo
import io.github.devholic.todox.db.TodoLabel
import rx.Observable
import java.util.*
import javax.inject.Inject

class TodoCreateInteractor @Inject constructor(val db: BriteDatabase, val context: Context) {

    private val labelQuery = "SELECT * FROM ${TodoLabel.TABLE}"

    fun getLabelList(): Observable<List<TodoLabel>> {
        return db.createQuery(TodoLabel.TABLE, labelQuery)
                .mapToList { TodoLabel.fromCursor(it) }
    }

    fun getSelectedLabelString(labelList: List<TodoLabel>, selectedId: ArrayList<Int>): String {

        val selectedLabel = ArrayList<TodoLabel>()

        for (label in labelList) {
            if (selectedId.indexOf(label.id) != -1) {
                selectedLabel.add(label)
            }
        }

        val builder = StringBuilder()

        for ((id, label) in selectedLabel) {
            builder.append(label)
            builder.append(", ")
        }

        val result = builder.toString()

        if (result.length > 2) {
            return result.substring(0, result.length - 2)
        }
        return context.getString(R.string.todocreate_label_default)
    }

    fun saveTodo(id: Int = -1, todo: String, priority: Int, selectedLabel: ArrayList<Int>): Todo {

        if (id != -1) {
            return updateTodo(id, todo, priority, selectedLabel)
        }
        val todoObject = Todo(todo = todo, priority = priority,
                labelId = Todo.createLabelIdString(selectedLabel))
        val insertId = db.insert(Todo.TABLE, todoObject.toContentValue())

        return todoObject.copy(id = insertId.toInt())
    }

    private fun updateTodo(id: Int, todo: String, priority: Int, selectedLabel: ArrayList<Int>): Todo {

        val todoObject = Todo(id = id, todo = todo, priority = priority,
                labelId = Todo.createLabelIdString(selectedLabel))

        db.update(Todo.TABLE, todoObject.toContentValue(), "${Todo.ID}=?", Integer.toString(id))
        return todoObject
    }
}