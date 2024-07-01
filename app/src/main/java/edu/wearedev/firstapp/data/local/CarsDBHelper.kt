package edu.wearedev.firstapp.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import edu.wearedev.firstapp.data.local.CarrosContract.DATABASE_NAME
import edu.wearedev.firstapp.data.local.CarrosContract.SQL_DELETE_ENTRIES
import edu.wearedev.firstapp.data.local.CarrosContract.TABLE_CAR

class CarsDBHelper( context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(TABLE_CAR)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "DbCar.db"
    }
}