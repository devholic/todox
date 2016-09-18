package io.github.devholic.todox.home.view

interface HomeView : LabelFilterDialog.LabelFilterDialogCallback {

    fun hideDeleteSnackbar()
    fun setLayout()
    fun setTitle(title: String)
    fun showDeleteSnackbar(todo: String)
    fun showToast(msg: String, duration: Int)
    fun subscribeData()
    fun subscribeView()
}