package io.github.devholic.todox.db

import android.content.Context
import android.database.Cursor
import com.squareup.sqlbrite.BriteDatabase
import com.squareup.sqlbrite.SqlBrite
import rx.schedulers.Schedulers

object DB {

    private val sqlbrite: SqlBrite by lazy { SqlBrite.create() }

    private var database: BriteDatabase? = null

    fun getInstance(context: Context): BriteDatabase {
        if (database == null) {

            val helper = DBHelper(context.applicationContext)

            database = sqlbrite.wrapDatabaseHelper(helper, Schedulers.io())
        }
        return database!!
    }

    fun getInt(cursor: Cursor, column: String): Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(column))
    }

    fun getString(cursor: Cursor, column: String): String {
        return cursor.getString(cursor.getColumnIndexOrThrow(column))
    }
}