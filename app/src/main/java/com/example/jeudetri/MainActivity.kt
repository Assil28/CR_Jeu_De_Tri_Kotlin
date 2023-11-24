package com.example.jeudetri

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.*

import androidx.compose.material.*
import androidx.compose.ui.Modifier

import com.example.jeudetri.ui.theme.JeuDeTriTheme

import androidx.room.Room


class MainActivity : ComponentActivity() {
    private val viewModel = MainViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ScoreTimingRecord(this@MainActivity).getTimeAll(BD_Sqlite(this@MainActivity))
        setContent {
            JeuDeTriTheme {

                DragableScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.primary)
                ) {
                    MainScreen(viewModel, context = this@MainActivity)
                }
            }
        }


    }


    }




