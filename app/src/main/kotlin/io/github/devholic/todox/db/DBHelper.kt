package io.github.devholic.todox.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context?) : SQLiteOpenHelper(context, "todox.db", null, 2) {

    private val createTodoLabel =
            "CREATE TABLE ${TodoLabel.TABLE} (" +
                    "${TodoLabel.ID} INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "${TodoLabel.LABEL} TEXT NOT NULL" +
                    ")"
    private val createTodo =
            "CREATE TABLE ${Todo.TABLE} (" +
                    "${Todo.ID} INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "${Todo.LABEL_ID} TEXT NULL," +
                    "${Todo.TODO} TEXT NOT NULL," +
                    "${Todo.PRIORITY} INTEGER NOT NULL" +
                    ")"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createTodoLabel)
        db?.execSQL(createTodo)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        val v = oldVersion

        if (v == 1) {
            db?.execSQL(createTodo)
        }
    }
}