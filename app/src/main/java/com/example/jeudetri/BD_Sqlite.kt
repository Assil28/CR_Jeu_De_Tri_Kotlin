package com.example.jeudetri

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class BD_Sqlite(context: Context?) : SQLiteOpenHelper(context, DBname, null, 1) {

    companion object {
        const val DBname = "data_db"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL("CREATE TABLE IF NOT EXISTS Time_Score(id INTEGER PRIMARY KEY AUTOINCREMENT, time LONG)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS Time_Score")
        onCreate(db)
    }

    fun AjoutTime(Time: Long?): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("time", Time)

        val Res = db.insert("Time_Score", null, contentValues)
        return if (Res == -1L) {
            false
        } else {
            true
        }
    }

    fun getAllRecord(): ArrayList<TimerScore>? {
        val arrayList: ArrayList<TimerScore> = ArrayList<TimerScore>()
        val db = this.readableDatabase
        val res = db.rawQuery("SELECT * FROM Time_Score", null)
        res.moveToFirst()
        while (res.isAfterLast == false) {
            //println(res.getString(2).toLong())
            val t1 = res.getString(1).toLong();
          //  val t2 = res.getString(2).toLong();
           val timerScoreInstance = TimerScore(t1);
            arrayList.add(timerScoreInstance)
            res.moveToNext()
        }

        return arrayList
    }
}
