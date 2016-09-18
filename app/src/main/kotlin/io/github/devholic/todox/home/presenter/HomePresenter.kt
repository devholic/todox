package io.github.devholic.todox.home.presenter

import android.content.Context
import android.widget.Toast
import com.squareup.sqlbrite.BriteDatabase
import io.github.devholic.todox.R
import io.github.devholic.todox.db.Todo
import io.github.devholic.todox.db.TodoLabel
import io.github.devholic.todox.home.model.HomeInteractor
import io.github.devholic.todox.home.view.HomeView
import rx.Observable
import rx.Subscription
import rx.lang.kotlin.onErrorReturnNull
import rx.subscriptions.CompositeSubscription
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomePresenter @Inject constructor(val db: BriteDatabase, val context: Context) : IHomePresenter {

    @Inject lateinit var interactor: HomeInteractor

    private val subscription: CompositeSubscription by lazy { CompositeSubscription() }

    private var backPressed = false
    private var deleteCache: Todo? = null
    private var isExit = false
    private var querySubscription: Subscription? = null
    private var view: HomeView? = null

    override fun addSubscription(s: Subscription) {
        subscription.add(s)
    }

    override fun clearSubscription() {
        subscription.clear()
        if (querySubscription != null && !querySubscription!!.isUnsubscribed) {
            querySubscription!!.unsubscribe()
        }
    }

    override fun deleteTodo(todo: Todo) {
        deleteCache = todo
        interactor.deleteTodo(todo.id)
        view?.showDeleteSnackbar(todo.todo)
    }

    override fun getLabelList(): Observable<List<TodoLabel>> {
        return interactor.getLabelList()
    }

    override fun getTodoList(): Observable<List<Todo>> {
        return interactor.getTodoList()
    }

    override fun onBackPressed(): Boolean {
        if (!backPressed) {
            backPressed = true
            view?.showToast(context.getString(R.string.home_cancel_check), Toast.LENGTH_SHORT)
            addSubscription(
                    Observable.timer(3, TimeUnit.SECONDS)
                            .onErrorReturnNull()
                            .doOnCompleted { backPressed = false }
                            .doOnUnsubscribe { backPressed = false }
                            .subscribe())
            return false
        }
        isExit = true
        return isExit
    }

    override fun recoverTodo() {
        if (deleteCache != null) {
            interactor.recoverTodo(deleteCache!!)
        }
    }

    override fun setQuerySubscription(s: Subscription) {
        if (querySubscription != null && !querySubscription!!.isUnsubscribed) {
            querySubscription!!.unsubscribe()
        }
        querySubscription = s
    }

    override fun setView(view: HomeView) {
        this.view = view
    }
}