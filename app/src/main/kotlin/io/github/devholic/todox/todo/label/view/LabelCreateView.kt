package io.github.devholic.todox.todo.label.view

interface LabelCreateView : LabelTextDialog.LabelTextDialogCallback {

    fun setLayout()
    fun showLabelDialog(id: Int?, label: String?)
    fun subscribeView()
}