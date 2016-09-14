package io.github.devholic.todox.db

import android.database.Cursor
import nz.bradcampbell.paperparcel.PaperParcel
import nz.bradcampbell.paperparcel.PaperParcelable

@PaperParcel
data class TodoLabel(val id: Int = -1, val label: String) : PaperParcelable {

    companion object {

        @JvmField val CREATOR = PaperParcelable.Creator(TodoLabel::class.java)

        val TABLE = "TodoLabel"
        val ID = "id"
        val LABEL = "label"

        fun fromCursor(cursor: Cursor): TodoLabel {

            val id = DB.getInt(cursor, ID)
            val label = DB.getString(cursor, LABEL)

            return TodoLabel(id, label)
        }
    }
}