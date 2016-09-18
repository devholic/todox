package io.github.devholic.todox.todo.create.view

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.jakewharton.rxbinding.view.RxView
import com.jakewharton.rxbinding.widget.RxAdapterView
import com.jakewharton.rxbinding.widget.RxTextView
import io.github.devholic.todox.R
import io.github.devholic.todox.TodoxApplication
import io.github.devholic.todox.dagger.component.ActivityComponent
import io.github.devholic.todox.dagger.component.DaggerActivityComponent
import io.github.devholic.todox.dagger.module.ActivityModule
import io.github.devholic.todox.db.TodoLabel
import io.github.devholic.todox.db.TodoLabelParcel
import io.github.devholic.todox.todo.create.presenter.TodoCreatePresenter
import kotlinx.android.synthetic.main.activity_todo_create.*
import java.util.*
import javax.inject.Inject

class TodoCreateActivity : AppCompatActivity(), TodoCreateView {

    @Inject lateinit var presenter: TodoCreatePresenter

    var component: ActivityComponent? = null
        get

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_create)
        component = DaggerActivityComponent.builder()
                .todoxComponent((application as TodoxApplication).component())
                .activityModule(ActivityModule(this))
                .build()
        component?.inject(this)
        setLayout()
    }

    override fun onResume() {
        super.onResume()
        subscribeData()
        subscribeView()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        presenter.clearSubscription()
    }

    override fun dialogLabelSelected(selectedId: ArrayList<Int>) {
        presenter.onLabelSelected(selectedId)
    }

    override fun showLabelDialog(data: List<TodoLabel>, selected: IntArray) {

        val d = LabelSelectDialog()
        val b = Bundle()
        val a = ArrayList<TodoLabelParcel>()

        for (label in data) {
            a.add(TodoLabelParcel(label))
        }

        b.putParcelableArrayList("data", a)
        b.putIntArray("selected", selected)
        d.arguments = b
        d.show(supportFragmentManager, "label_select_dialog")
    }

    override fun showSnackbar(msg: String, duration: Int) {
        Snackbar.make(coordinator_layout, msg, duration).show()
    }

    override fun subscribeData() {
        presenter.addSubscription(
                presenter
                        .getLabelList()
                        .subscribe({ presenter.updateLabelList(it) })
        )
    }

    override fun subscribeView() {
        presenter.addSubscription(
                RxView.clicks(action_btn)
                        .onBackpressureDrop()
                        .subscribe({
                            if (presenter.saveTodo() != null) {
                                finish()
                            }
                        }))
        presenter.addSubscription(
                RxView.clicks(label_selector)
                        .onBackpressureDrop()
                        .subscribe({
                            presenter.showLabelSelectDialog()
                        })
        )
        presenter.addSubscription(
                RxAdapterView
                        .itemSelections(priority_spinner)
                        .map { 4 - it }
                        .subscribe({ presenter.onPriorityChanged(it) }))
        presenter.addSubscription(
                RxTextView.textChanges(todo)
                        .onBackpressureBuffer()
                        .map { it.toString() }
                        .subscribe({
                            presenter.onTodoChanged(it)
                        })
        )
    }

    override fun updateLabelText(text: String) {
        label.text = text
    }

    private fun setLayout() {
        setSupportActionBar(toolbar)
        presenter.setView(this)
        with(supportActionBar!!) {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            setTitle(R.string.todocreate_title)
        }
    }
}