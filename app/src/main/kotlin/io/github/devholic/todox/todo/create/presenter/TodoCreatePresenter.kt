package io.github.devholic.todox.todo.create.presenter

import android.content.Context
import android.support.design.widget.Snackbar
import com.squareup.sqlbrite.BriteDatabase
import io.github.devholic.todox.R
import io.github.devholic.todox.db.Todo
import io.github.devholic.todox.db.TodoLabel
import io.github.devholic.todox.todo.create.model.TodoCreateInteractor
import io.github.devholic.todox.todo.create.view.TodoCreateView
import rx.Observable
import rx.Subscription
import rx.subscriptions.CompositeSubscription
import java.util.*
import javax.inject.Inject

class TodoCreatePresenter @Inject constructor(val db: BriteDatabase, val context: Context) : ITodoCreatePresenter {

    @Inject lateinit var interactor: TodoCreateInteractor

    val subscription: CompositeSubscription by lazy { CompositeSubscription() }

    private val id: Int = -1
    private var labelList: List<TodoLabel> = Collections.emptyList()
    private var priority: Int = 4
    private var selected: ArrayList<Int> = ArrayList()
    private var todo: String = ""
    private var view: TodoCreateView? = null

    override fun addSubscription(s: Subscription) {
        subscription.add(s)
    }

    override fun clearSubscription() {
        subscription.clear()
    }

    override fun getLabelList(): Observable<List<TodoLabel>> {
        return interactor.getLabelList()
    }

    override fun onLabelSelected(selected: ArrayList<Int>) {
        this.selected = selected
        view?.updateLabelText(interactor.getSelectedLabelString(labelList, selected))
    }

    override fun onPriorityChanged(priority: Int) {
        this.priority = priority
    }

    override fun onTodoChanged(todo: String) {
        this.todo = todo
    }

    override fun saveTodo(): Todo? {
        if (todo.length == 0) {
            view?.showSnackbar(context.getString(R.string.todocreate_todo_empty), Snackbar.LENGTH_SHORT)
            return null
        }
        return interactor.saveTodo(id = id, todo = todo, priority = priority, selectedLabel = selected)
    }

    override fun setView(view: TodoCreateView) {
        this.view = view
    }

    override fun showLabelSelectDialog() {
        view?.showLabelDialog(labelList, selected.toIntArray())
    }

    override fun updateLabelList(list: List<TodoLabel>) {
        labelList = list
    }
}