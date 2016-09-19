package io.github.devholic.todox.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context?) : SQLiteOpenHelper(context, "todox.db", null, 3) {

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

    private val updateV3 = "SELECT * FROM ${Todo.TABLE}"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createTodoLabel)
        db.execSQL(createTodo)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        var v = oldVersion

        if (v == 1) {
            db.execSQL(createTodo)
            v++
        }
        if (v == 2) {
            try {
                db.beginTransaction()
                val cursor = db.rawQuery(updateV3, null)
                if (cursor != null && cursor.count != 0) {
                    cursor.moveToFirst()
                    while (cursor.moveToNext()) {

                        val prevObject = Todo.fromCursor(cursor)
                        val value = ContentValues()

                        value.put(Todo.LABEL_ID, getV3Label(prevObject.labelId))
                        db.update(Todo.TABLE, value, "${Todo.ID} = ?", arrayOf(Integer.toString(prevObject.id)))
                    }
                    cursor.close()
                }
                db.setTransactionSuccessful()
            } catch (e: SQLiteException) {
                e.printStackTrace()
            } finally {
                db.endTransaction()
            }
        }
    }

    private fun getV3Label(labelId: String): String {
        if (labelId.length == 0) {
            return labelId
        }

        val builder = StringBuilder()

        builder.append(",")
        builder.append(labelId)
        builder.append(",")
        return builder.toString()
    }
}