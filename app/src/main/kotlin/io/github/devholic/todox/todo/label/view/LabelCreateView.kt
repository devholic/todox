package io.github.devholic.todox.todo.label.view

interface LabelCreateView : LabelTextDialog.LabelTextDialogCallback {

    fun showLabelDialog(id: Int?, label: String?)
    fun subscribeView()
}