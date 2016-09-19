package io.github.devholic.todox.base

import rx.Subscription

interface BasePresenter {

    fun addSubscription(s: Subscription)
    fun clearSubscription()
}