package io.github.devholic.todox.home.view

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.jakewharton.rxbinding.view.RxView
import io.github.devholic.todox.R
import io.github.devholic.todox.TodoxApplication
import io.github.devholic.todox.dagger.component.DaggerActivityComponent
import io.github.devholic.todox.dagger.module.ActivityModule
import io.github.devholic.todox.db.TodoLabelParcel
import io.github.devholic.todox.db.TodoParcel
import io.github.devholic.todox.home.adapter.TodoAdapter
import io.github.devholic.todox.home.presenter.HomePresenter
import io.github.devholic.todox.todo.create.view.TodoCreateActivity
import io.github.devholic.todox.todo.label.view.LabelCreateActivity
import kotlinx.android.synthetic.main.activity_base.*
import rx.android.schedulers.AndroidSchedulers
import java.util.*
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), HomeView {

    @Inject lateinit var presenter: HomePresenter

    private val linearLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(this) }
    private val todoAdapter: TodoAdapter by lazy { TodoAdapter() }

    private var snackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        DaggerActivityComponent.builder()
                .todoxComponent((application as TodoxApplication).component())
                .activityModule(ActivityModule(this))
                .build()
                .inject(this)
        setLayout()
    }

    override fun onResume() {
        super.onResume()
        subscribeData()
        subscribeView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onBackPressed() {
        if (presenter.onBackPressed()) {
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_filter -> {

                val dialog = LabelFilterDialog()
                val bundle = Bundle()

                val labelList = ArrayList<TodoLabelParcel>()

                presenter.getLabelList().forEach { labelList.add(TodoLabelParcel(it)) }
                bundle.putParcelableArrayList("labelList", labelList)
                dialog.arguments = bundle
                dialog.show(supportFragmentManager, "label_filter_dialog")
                return true
            }
            R.id.action_label -> {

                val intent = Intent(this, LabelCreateActivity::class.java)

                startActivity(intent)
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        hideDeleteSnackbar()
        presenter.clearSubscription()
    }

    override fun hideDeleteSnackbar() {
        snackBar?.dismiss()
    }

    override fun onLabelSelected(id: Int) {
        presenter.setLabelFilter(id)
        presenter.setQuerySubscription(
                presenter
                        .getTodoListObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(todoAdapter)
        )
    }

    override fun setLayout() {
        setSupportActionBar(toolbar)
        presenter.setView(this)
        with(recycler_view) {
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = linearLayoutManager
            adapter = todoAdapter
        }
    }

    override fun setTitle(title: String) {
        with(supportActionBar!!) {
            setTitle(title)
        }
    }

    override fun showDeleteSnackbar(todo: String) {

        snackBar = Snackbar.make(coordinator_layout, "", Snackbar.LENGTH_LONG)

        with(snackBar!!) {
            setAction(getString(R.string.home_undo), {
                presenter.undoDelete()
                dismiss()
            })
            setText("\"$todo\" ${getString(R.string.home_deleted)}.")
            show()
        }
    }

    override fun showToast(msg: String, duration: Int) {
        Toast.makeText(this, msg, duration).show()
    }

    override fun subscribeData() {

        val labelObservable = presenter.getLabelListObservable()
                .observeOn(AndroidSchedulers.mainThread())

        presenter.addSubscription(
                labelObservable
                        .subscribe({
                            presenter.setLabelList(it)
                        })
        )
        presenter.addSubscription(
                labelObservable
                        .map {

                            val hash = HashMap<Int, String>()

                            it.forEach { label -> hash.put(label.id, label.label) }
                            return@map hash
                        }
                        .subscribe({
                            todoAdapter.setLabelMap(it)
                            presenter.setLabelMap(it)
                        })
        )
    }

    override fun subscribeView() {
        presenter.addSubscription(todoAdapter.getCheckboxEventObservable()
                .onBackpressureDrop()
                .subscribe {
                    presenter.deleteTodo(it)
                })
        presenter.addSubscription(todoAdapter.getClickEventObservable()
                .onBackpressureDrop()
                .subscribe({

                    val intent = Intent(this, TodoCreateActivity::class.java)

                    intent.putExtra("todo", TodoParcel(it))
                    startActivity(intent)
                }))
        presenter.addSubscription(RxView.clicks(action_btn)
                .onBackpressureDrop()
                .subscribe {

                    val intent = Intent(this, TodoCreateActivity::class.java)

                    startActivity(intent)
                })
        presenter.setQuerySubscription(
                presenter
                        .getTodoListObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(todoAdapter)
        )
    }
}
