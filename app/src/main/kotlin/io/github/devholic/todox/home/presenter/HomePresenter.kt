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
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomePresenter @Inject constructor(val db: BriteDatabase, val context: Context) : IHomePresenter {

    @Inject lateinit var interactor: HomeInteractor

    private val subscription: CompositeSubscription by lazy { CompositeSubscription() }

    private var backPressed = false
    private var deleteCache: Todo? = null
    private var filter: Int = -1
    private var isExit = false
    private var label = HashMap<Int, String>()
    private var labelList: List<TodoLabel> = Collections.emptyList()
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

    override fun getLabelList(): List<TodoLabel> {
        return labelList
    }

    override fun getLabelListObservable(): Observable<List<TodoLabel>> {
        return interactor.getLabelList()
    }

    override fun getTodoListObservable(): Observable<List<Todo>> {
        if (filter == -1) {
            return interactor.getTodoList()
        }
        return interactor.getFilteredTodoList(filter)
    }

    override fun onBackPressed(): Boolean {
        if (!backPressed) {
            backPressed = true
            view?.showToast(context.getString(R.string.home_cancel_check), Toast.LENGTH_SHORT)
            addSubscription(
                    Observable.timer(2, TimeUnit.SECONDS)
                            .onErrorReturnNull()
                            .doOnCompleted { backPressed = false }
                            .doOnUnsubscribe { backPressed = false }
                            .subscribe())
            return false
        }
        isExit = true
        return isExit
    }

    override fun setLabelFilter(id: Int) {
        this.filter = id
        if (label.containsKey(id)) {
            view?.setTitle("#${label[id]!!}")
            return
        }
        view?.setTitle(context.getString(R.string.app_name))
    }

    override fun setLabelMap(label: HashMap<Int, String>) {
        this.label = label
    }

    override fun setLabelList(label: List<TodoLabel>) {
        this.labelList = label
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

    override fun undoDelete() {
        if (deleteCache != null) {
            interactor.recoverTodo(deleteCache!!)
        }
    }
}