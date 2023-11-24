package com.example.jeudetri


import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.random.Random.Default.nextInt

class MainViewModel :ViewModel() {

    var isCurrentlyDragging by mutableStateOf(false)
        private set

    var items by mutableStateOf(emptyList<NumberUiItem>())
        private set

    var addedNumbers = mutableStateListOf<NumberUiItem>()
        private set

    init {
        items = listOf(
            NumberUiItem((0..10).random(),"1", Color.Gray),
            NumberUiItem((0..10).random(),"2", Color.Blue),
            NumberUiItem((0..10).random(),"3", Color.Green),
            NumberUiItem((0..10).random(),"4", Color.Cyan),
            NumberUiItem((0..10).random(),"5", Color.Magenta),
        )
    }

    fun startDragging(){
        isCurrentlyDragging = true
    }
    fun stopDragging(){
        isCurrentlyDragging = false
    }

    fun addNumber(numberUiItem: NumberUiItem){

            addedNumbers.add(numberUiItem)

        println("tol : "+addedNumbers.size)
        if(addedNumbers.size>4){
    println("kamelt")

        }


    }

    fun sizeTable():Int{

        return addedNumbers.size;
    }

}