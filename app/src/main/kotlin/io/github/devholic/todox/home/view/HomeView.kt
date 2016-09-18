package io.github.devholic.todox.home.view

interface HomeView {

    fun hideDeleteSnackbar()
    fun setLayout()
    fun showDeleteSnackbar(todo: String)
    fun showToast(msg: String, duration: Int)
    fun subscribeData()
    fun subscribeView()
}