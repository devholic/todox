package io.github.devholic.todox.todo.create.view

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
import io.github.devholic.todox.todo.create.adapter.LabelSelectAdapter
import kotlinx.android.synthetic.main.dialog_labelselect.view.*
import java.util.*

class LabelSelectDialog : AppCompatDialogFragment() {

    private val linearLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(context) }

    private var labelList: ArrayList<TodoLabel> = ArrayList()
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

        val selected = arguments.getIntArray("selected")

        selectedId.clear()
        selected.forEach { selectedId.add(it) }

        val passedLabel = arguments.getParcelableArrayList<TodoLabelParcel>("labelList")

        labelList.clear()
        passedLabel.forEach { labelList.add(it.data) }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val v = LayoutInflater.from(context).inflate(R.layout.dialog_labelselect, null)

        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        v.recycler_view.layoutManager = linearLayoutManager
        v.recycler_view.adapter = LabelSelectAdapter(labelList, selectedId)

        val builder = AlertDialog.Builder(activity)
                .setTitle(context.getString(R.string.labelselect_dialog_title))
                .setPositiveButton(getString(R.string.labeltext_dialog_positive), { d, pos ->
                    callback.onLabelSelected((v.recycler_view.adapter as LabelSelectAdapter).selected)
                    dismiss()
                })
                .setNegativeButton(getString(R.string.labeltext_dialog_negative), { d, pos ->
                    dismiss()
                })
                .setView(v)

        return builder.create()
    }

    interface LabelSelectDialogCallback {

        fun onLabelSelected(selectedId: ArrayList<Int>)
    }
}