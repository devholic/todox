package io.github.devholic.todox.db

import android.database.Cursor

object DB {

    fun getInt(cursor: Cursor, column: String): Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(column))
    }

    fun getString(cursor: Cursor, column: String): String {
        return cursor.getString(cursor.getColumnIndexOrThrow(column))
    }
}