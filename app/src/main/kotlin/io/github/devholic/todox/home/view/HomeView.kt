package io.github.devholic.todox.home.view

import io.github.devholic.todox.base.BaseView

interface HomeView : BaseView, LabelFilterDialog.LabelFilterDialogCallback {

    fun hideDeleteSnackbar()
    fun setTitle(title: String)
    fun showDeleteSnackbar(todo: String)
    fun showToast(msg: String, duration: Int)
    fun subscribeData()
}