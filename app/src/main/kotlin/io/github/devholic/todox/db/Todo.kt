package io.github.devholic.todox.db

import android.content.ContentValues
import android.database.Cursor
import nz.bradcampbell.paperparcel.PaperParcel
import nz.bradcampbell.paperparcel.PaperParcelable
import java.util.*

@PaperParcel
data class Todo(val id: Int = -1, val labelId: String, val todo: String, val priority: Int = 4) : PaperParcelable {

    fun toContentValue(): ContentValues {

        val content = ContentValues()

        if (id != -1) {
            content.put(ID, id)
        }
        content.put(LABEL_ID, labelId)
        content.put(TODO, todo)
        content.put(PRIORITY, priority)
        return content
    }

    companion object {

        @JvmField val CREATOR = PaperParcelable.Creator(Todo::class.java)

        val TABLE = "Todo"
        val ID = "id"
        val LABEL_ID = "labelid"
        val TODO = "string"
        val PRIORITY = "priority"

        fun createLabelIdString(list: ArrayList<Int>): String {

            val builder = StringBuilder()

            for (id in list) {
                builder.append(id)
                builder.append(",")
            }

            val result = builder.toString()

            if (result.length > 0) {
                return result.substring(0, result.length - 1)
            }
            return result
        }

        fun fromCursor(cursor: Cursor): Todo {

            val id = DB.getInt(cursor, ID)
            val labelId = DB.getString(cursor, LABEL_ID)
            val todo = DB.getString(cursor, TODO)
            val priority = DB.getInt(cursor, PRIORITY)

            return Todo(id, labelId, todo, priority)
        }

        fun parseLabelId(idString: String): ArrayList<Int> {

            val idList = idString.split(",")
            val parsed = ArrayList<Int>()

            idList
                    .filter { it.length > 0 }
                    .forEach {
                        parsed.add(Integer.parseInt(it))
                    }
            return parsed
        }
    }
}