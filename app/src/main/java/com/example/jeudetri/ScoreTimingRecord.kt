package com.example.jeudetri

import android.content.Context
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.ViewModel


public class ScoreTimingRecord(context: Context) {
    val db = BD_Sqlite(context)





    public fun getTimeAll(db: BD_Sqlite): ArrayList<TimerScore>? {
        val array: ArrayList<TimerScore>;
        array = db.getAllRecord()!!

        val sortedArray = array.sortedBy { it.time }

        // Prendre les 10 premiers éléments
        val top10Array = ArrayList(sortedArray.take(10))

        return top10Array

    }
}