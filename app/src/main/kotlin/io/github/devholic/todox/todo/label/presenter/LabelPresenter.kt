package io.github.devholic.todox.todo.label.presenter

import com.squareup.sqlbrite.BriteDatabase
import io.github.devholic.todox.db.TodoLabel
import io.github.devholic.todox.todo.label.model.LabelInteractor
import rx.Observable
import rx.Subscription
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

class LabelPresenter @Inject constructor(val db: BriteDatabase) : ILabelPresenter {

    @Inject lateinit var interactor: LabelInteractor

    val subscription: CompositeSubscription by lazy { CompositeSubscription() }

    override fun addSubscription(s: Subscription) {
        subscription.add(s)
    }

    override fun clearSubscription() {
        subscription.clear()
    }

    override fun getLabelList(): Observable<List<TodoLabel>> {
        return interactor.getLabelList()
    }

    override fun saveLabel(id: Int, label: String) {
        interactor.saveLabel(id, label)
    }
}