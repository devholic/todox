package io.github.devholic.todox.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context?) : SQLiteOpenHelper(context, "todox.db", null, 1) {

    private val createTodoLabel =
            "CREATE TABLE ${TodoLabel.TABLE} (" +
                    "${TodoLabel.ID} INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "${TodoLabel.LABEL} TEXT NOT NULL" +
                    ")"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createTodoLabel)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}