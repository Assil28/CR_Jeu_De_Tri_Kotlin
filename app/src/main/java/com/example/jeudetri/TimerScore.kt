package com.example.jeudetri

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "time_table")
data class TimerScore (


    val time: Long

)