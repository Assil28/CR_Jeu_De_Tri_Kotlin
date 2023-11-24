package com.example.jeudetri


import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey


data class NumberUiItem(
    val num:Int,
    val id:String,
    val backgroundColor: Color
)