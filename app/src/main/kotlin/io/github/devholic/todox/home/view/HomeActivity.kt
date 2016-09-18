package io.github.devholic.todox.home.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.jakewharton.rxbinding.view.RxView
import io.github.devholic.todox.R
import io.github.devholic.todox.TodoxApplication
import io.github.devholic.todox.dagger.component.DaggerActivityComponent
import io.github.devholic.todox.dagger.module.ActivityModule
import io.github.devholic.todox.home.presenter.HomePresenter
import io.github.devholic.todox.todo.create.view.TodoCreateActivity
import io.github.devholic.todox.todo.label.view.LabelCreateActivity
import kotlinx.android.synthetic.main.activity_base.*
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), HomeView {

    @Inject lateinit var presenter: HomePresenter

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

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun setLayout() {
        setSupportActionBar(toolbar)
        presenter.setView(this)
        with(action_btn, {
            presenter.addSubscription(RxView.clicks(this)
                    .onBackpressureDrop()
                    .subscribe {

                        val intent = Intent(context, TodoCreateActivity::class.java)

                        startActivityForResult(intent, HomePresenter.ADD_REQUEST)
                    })
        })
    }

    override fun showToast(msg: String, duration: Int) {
        Toast.makeText(this, msg, duration).show()
    }
}
