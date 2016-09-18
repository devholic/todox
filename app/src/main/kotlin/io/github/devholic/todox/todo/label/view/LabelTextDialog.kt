package io.github.devholic.todox.todo.label.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialogFragment
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import io.github.devholic.todox.R

class LabelTextDialog : AppCompatDialogFragment() {

    private lateinit var callback: LabelTextDialogCallback

    private var content: String = ""
    private var labelId: Int = -1
    private var title: String = ""

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            callback = activity as LabelTextDialogCallback
        } catch (e: ClassCastException) {
            throw(e)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments.getString("title", "")
        labelId = arguments.getInt("id", -1)
        content = arguments.getString("content", "")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val v = LayoutInflater.from(context).inflate(R.layout.dialog_labeltext, null)
        val labelInput = (v.findViewById(R.id.label_input) as EditText)

        labelInput.setText(content, TextView.BufferType.EDITABLE)

        val b = AlertDialog.Builder(activity)
                .setTitle(title)
                .setPositiveButton(getString(R.string.labeltext_dialog_positive), { d, pos ->
                    if (labelInput.text.length > 0) {
                        dismiss()
                        callback.dialogLabelEntered(labelId, labelInput.text.toString())
                    }
                    Toast.makeText(context, context.getString(R.string.labeltext_dialog_empty),
                            Toast.LENGTH_SHORT).show()
                })
                .setNegativeButton(getString(R.string.labeltext_dialog_negative), { d, pos -> dismiss() })
                .setView(v)

        return b.create()
    }

    interface LabelTextDialogCallback {

        fun dialogLabelEntered(id: Int, label: String)
    }
}