package io.github.devholic.todox.todo.label.view

import io.github.devholic.todox.base.BaseView

interface LabelCreateView : BaseView, LabelTextDialog.LabelTextDialogCallback {

    fun showLabelDialog(id: Int?, label: String?)
}