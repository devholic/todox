package io.github.devholic.todox.home.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import io.github.devholic.todox.R
import io.github.devholic.todox.db.TodoLabel
import io.github.devholic.todox.db.TodoLabelParcel
import io.github.devholic.todox.home.adapter.LabelFilterAdapter
import kotlinx.android.synthetic.main.dialog_labelselect.view.*
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.CompositeSubscription
import java.util.*

class LabelFilterDialog : AppCompatDialogFragment() {

    private val linearLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(context) }
    private val subscription: CompositeSubscription by lazy { CompositeSubscription() }

    private var adapter: LabelFilterAdapter? = null
    private var labelList: ArrayList<TodoLabel> = ArrayList()

    private lateinit var callback: LabelFilterDialogCallback

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            callback = activity as LabelFilterDialogCallback
        } catch (e: ClassCastException) {
            throw(e)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val passedLabel = arguments.getParcelableArrayList<TodoLabelParcel>("labelList")

        labelList.clear()
        labelList.add(TodoLabel(label = context.getString(R.string.home_action_filter_default)))
        passedLabel.forEach { labelList.add(it.data) }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val v = LayoutInflater.from(context).inflate(R.layout.dialog_labelselect, null)

        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        v.recycler_view.layoutManager = linearLayoutManager

        adapter = LabelFilterAdapter(labelList)

        v.recycler_view.adapter = adapter

        val builder = AlertDialog.Builder(activity)
                .setTitle(context.getString(R.string.home_action_filter))
                .setNegativeButton(getString(R.string.dialog_negative), { d, pos ->
                    dismiss()
                })
                .setView(v)

        return builder.create()
    }

    override fun onResume() {
        super.onResume()
        if (adapter != null) {
            subscription.add(
                    adapter!!.getClickEventObservable()
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                callback.onLabelSelected(it.id)
                                dismiss()
                            }))
        }
    }

    override fun onPause() {
        super.onPause()
        subscription.clear()
    }

    interface LabelFilterDialogCallback {

        fun onLabelSelected(id: Int)
    }
}