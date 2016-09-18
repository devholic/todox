package io.github.devholic.todox.home.presenter

import android.content.Context
import android.widget.Toast
import io.github.devholic.todox.R
import io.github.devholic.todox.home.view.HomeView
import rx.Observable
import rx.Subscription
import rx.lang.kotlin.onErrorReturnNull
import rx.subscriptions.CompositeSubscription
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomePresenter @Inject constructor(val context: Context) : IHomePresenter {

    private val subscription: CompositeSubscription by lazy { CompositeSubscription() }

    private var backPressed = false
    private var isExit = false
    private var view: HomeView? = null

    override fun addSubscription(s: Subscription) {
        subscription.add(s)
    }

    override fun clearSubscription() {
        subscription.clear()
    }

    override fun onBackPressed(): Boolean {
        if (!backPressed) {
            backPressed = true
            view?.showToast(context.getString(R.string.home_cancel_check), Toast.LENGTH_SHORT)
            addSubscription(
                    Observable.timer(3, TimeUnit.SECONDS)
                            .onErrorReturnNull()
                            .doOnCompleted { backPressed = false }
                            .subscribe())
            return false
        }
        isExit = true
        return isExit
    }

    override fun onStop() {
        if (isExit) {
            clearSubscription()
        }
    }

    override fun setView(view: HomeView) {
        this.view = view
    }
}