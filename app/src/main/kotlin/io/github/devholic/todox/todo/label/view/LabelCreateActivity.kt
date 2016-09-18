package io.github.devholic.todox.todo.label.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.jakewharton.rxbinding.view.RxView
import io.github.devholic.todox.R
import io.github.devholic.todox.TodoxApplication
import io.github.devholic.todox.dagger.component.DaggerActivityComponent
import io.github.devholic.todox.dagger.module.ActivityModule
import io.github.devholic.todox.todo.label.adapter.LabelAdapter
import io.github.devholic.todox.todo.label.presenter.LabelPresenter
import kotlinx.android.synthetic.main.activity_base.*
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class LabelCreateActivity : AppCompatActivity(), LabelCreateView {

    @Inject lateinit var presenter: LabelPresenter

    private val labelAdapter: LabelAdapter by lazy { LabelAdapter() }
    private val linearLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(this) }

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
        subscribeView()
    }

    override fun onPause() {
        super.onPause()
        presenter.clearSubscription()
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

    override fun onLabelEntered(id: Int, label: String) {
        presenter.saveLabel(id, label)
    }

    override fun setLayout() {
        setSupportActionBar(toolbar)
        with(supportActionBar!!, {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            setTitle(R.string.labelcreate_title)
        })
        with(recycler_view, {
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = linearLayoutManager
            adapter = labelAdapter
        })
    }

    override fun showLabelDialog(id: Int?, label: String?) {

        val dialog = LabelTextDialog()
        val bundle = Bundle()

        if (id == null && label == null) {
            bundle.putString("title", getString(R.string.labeltext_dialog_new_title))
        } else {
            bundle.putString("title", getString(R.string.labeltext_dialog_edit_title))
        }
        if (id != null) {
            bundle.putInt("id", id)
        }
        if (label != null) {
            bundle.putString("content", label)
        }
        dialog.arguments = bundle
        dialog.show(supportFragmentManager, "label_dialog")
    }

    override fun subscribeView() {
        presenter.addSubscription(
                presenter.getLabelList()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(labelAdapter)
        )
        presenter.addSubscription(
                labelAdapter
                        .getClickEventObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .onBackpressureDrop()
                        .subscribe({
                            showLabelDialog(it.id, it.label)
                        })
        )
        presenter.addSubscription(
                RxView.clicks(action_btn)
                        .onBackpressureDrop()
                        .subscribe({
                            showLabelDialog(null, null)
                        }))
    }
}
