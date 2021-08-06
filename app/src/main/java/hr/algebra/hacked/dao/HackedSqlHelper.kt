package hr.algebra.hacked.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import hr.algebra.hacked.model.ItemModel

private const val DB_NAME = "hacked.db"
private const val DB_VERSION = 1
private const val TABLE_NAME = "hackedPages"

private val CREATE_TABLE = "create table $TABLE_NAME( " +
        "${ItemModel::_id.name} integer primary key autoincrement, " +
        "${ItemModel::title.name} text not null, " +
        "${ItemModel::domain.name} text not null, " +
        "${ItemModel::pwnCount.name} integer not null, " +
        "${ItemModel::breachDate.name} text not null, " +
        "${ItemModel::description.name} text not null," +
        "${ItemModel::isVerified.name} integer not null," +
        "${ItemModel::isSensitive.name} integer not null," +
        "${ItemModel::isRetired.name} integer not null," +
        "${ItemModel::isSpamList.name} integer not null," +
        "${ItemModel::isSaved.name} integer not null" +

        ")"
private const val DROP_TABLE = "drop table $TABLE_NAME"

class HackedSqlHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION),
    HackedRepository {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_TABLE)

        onCreate(db)
    }

    override fun delete(selection: String?, selectionArgs: Array<String>?): Int =
        writableDatabase.delete(
            TABLE_NAME,selection,selectionArgs
        )


    override fun insert(values: ContentValues?)=writableDatabase.insert(TABLE_NAME,null,values)



    override fun query(
        projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? =readableDatabase.query(TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder)

    override fun update(
        values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    )=writableDatabase.update(TABLE_NAME,values,selection,selectionArgs)
}