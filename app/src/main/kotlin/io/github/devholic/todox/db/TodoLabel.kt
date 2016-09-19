package io.github.devholic.todox.db

import android.content.ContentValues
import android.database.Cursor
import nz.bradcampbell.paperparcel.PaperParcel
import nz.bradcampbell.paperparcel.PaperParcelable

@PaperParcel
data class TodoLabel(val id: Int = -1, val label: String) : PaperParcelable {

    fun toContentValue(): ContentValues {

        val content = ContentValues()

        if (id != -1) {
            content.put(TodoLabel.ID, id)
        }
        content.put(TodoLabel.LABEL, label)
        return content
    }

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