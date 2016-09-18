package io.github.devholic.todox.todo.create.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import io.github.devholic.todox.R
import io.github.devholic.todox.db.TodoLabel
import io.github.devholic.todox.db.TodoLabelParcel
import io.github.devholic.todox.todo.create.adapter.LabelSelectAdapter
import java.util.*

class LabelSelectDialog : AppCompatDialogFragment() {

    private val linearLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(context) }

    private var data: ArrayList<TodoLabel> = ArrayList()
    private var selectedId: ArrayList<Int> = ArrayList()

    private lateinit var callback: LabelSelectDialogCallback

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            callback = activity as LabelSelectDialogCallback
        } catch (e: ClassCastException) {
            throw(e)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rawData = arguments.getParcelableArrayList<TodoLabelParcel>("data")
        val selected = arguments.getIntArray("selected")

        selectedId.clear()
        for (s in selected) {
            selectedId.add(s)
        }

        data.clear()
        for (d in rawData) {
            data.add(d.data)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val v = LayoutInflater.from(context).inflate(R.layout.dialog_labelselect, null)
        val recyclerView = (v.findViewById(R.id.recycler_view) as RecyclerView)

        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = LabelSelectAdapter(data, selectedId)

        val b = AlertDialog.Builder(activity)
                .setTitle(context.getString(R.string.labelselect_dialog_title))
                .setPositiveButton(getString(R.string.labeltext_dialog_positive), { d, pos ->
                    callback.dialogLabelSelected((recyclerView.adapter as LabelSelectAdapter).selected)
                    dismiss()
                })
                .setNegativeButton(getString(R.string.labeltext_dialog_negative), { d, pos ->
                    dismiss()
                })
                .setView(v)

        return b.create()
    }

    interface LabelSelectDialogCallback {

        fun dialogLabelSelected(selectedId: ArrayList<Int>)
    }
}