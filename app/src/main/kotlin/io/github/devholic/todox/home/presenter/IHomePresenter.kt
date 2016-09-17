package io.github.devholic.todox.home.presenter

import io.github.devholic.todox.home.view.HomeView
import rx.Subscription

interface IHomePresenter {

    fun addSubscription(s: Subscription)
    fun clearSubscription()
    fun onBackPressed(): Boolean
    fun onStop()
    fun setView(view: HomeView)
}