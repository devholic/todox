package io.github.devholic.todox.todo.create.view

import io.github.devholic.todox.db.TodoLabel

interface TodoCreateView : LabelSelectDialog.LabelSelectDialogCallback {

    fun showLabelDialog(data: List<TodoLabel>, selected: IntArray)
    fun showSnackbar(msg: String, duration: Int)
    fun subscribeData()
    fun subscribeView()
    fun updateLabelText(text: String)
}
