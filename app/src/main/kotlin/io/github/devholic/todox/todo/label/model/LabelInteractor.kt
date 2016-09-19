package io.github.devholic.todox.todo.label.model

import com.squareup.sqlbrite.BriteDatabase
import io.github.devholic.todox.db.TodoLabel
import rx.Observable
import javax.inject.Inject

class LabelInteractor @Inject constructor(val db: BriteDatabase) {

    private val labelQuery = "SELECT * FROM ${TodoLabel.TABLE}"

    fun getLabelList(): Observable<List<TodoLabel>> {
        return db.createQuery(TodoLabel.TABLE, labelQuery)
                .mapToList { TodoLabel.fromCursor(it) }
    }

    fun saveLabel(id: Int = -1, label: String): TodoLabel {
        if (id != -1) {
            return updateLabel(id, label)
        }

        val labelObject = TodoLabel(label = label)
        val rowId = db.insert(TodoLabel.TABLE, labelObject.toContentValue())

        return labelObject.copy(id = rowId.toInt())
    }

    private fun updateLabel(id: Int, label: String): TodoLabel {

        val labelObject = TodoLabel(id = id, label = label)

        db.update(TodoLabel.TABLE, labelObject.toContentValue(), "${TodoLabel.ID}=?", Integer.toString(id))
        return labelObject
    }
}