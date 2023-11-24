package com.example.jeudetri

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContentProviderCompat.requireContext
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
     context: Context
) {
    val scoreTimingRecord = ScoreTimingRecord(context)
    val db = BD_Sqlite(context)
    val screenWidth = LocalConfiguration.current.screenWidthDp

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Text(
            text = "Tier in increasing order of number drop the number on the mid of the screen",

            style = MaterialTheme.typography.body1,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize=15.sp,
            modifier = Modifier.padding(top = 20.dp).align(Alignment.CenterHorizontally)
        )

        Button(onClick = {
            if (!isRunning) {
                startChronometer()
                getTimeAll(db)
            }
        },
            modifier = Modifier.padding(top = 0.dp)

        ) {
            Text(text = "Start"
                , color = MaterialTheme.colors.onSecondary
            )
        }

        Text(text = chronoText, fontSize = 30.sp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){

            mainViewModel.items.forEach { number ->
                DragTarget(
                    dataToDrop = number,
                    viewModel = mainViewModel
                ) {
                    Box(
                        modifier = Modifier
                            .size(Dp(screenWidth / 7f))
                            .clip(RoundedCornerShape(10.dp))
                            .shadow(7.dp, RoundedCornerShape(10.dp))
                            .background(number.backgroundColor, RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center,
                    ){
                        Text(
                            text = number.num.toString(),
                            style = MaterialTheme.typography.body1,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
        AnimatedVisibility(
            mainViewModel.isCurrentlyDragging,
            enter = slideInHorizontally (initialOffsetX = {it})
        ) {
            DropItem<NumberUiItem>(
                modifier = Modifier
                    .size(Dp(screenWidth / 3.5f))
            ) { isInBound, numberItem ->
                if(numberItem != null){
                    LaunchedEffect(key1 = numberItem){
                        mainViewModel.addNumber(numberItem)
                    }
                }
                if(isInBound){
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .border(
                                1.dp,
                                color = Color.Red,
                                shape = RoundedCornerShape(15.dp)
                            )
                            .background(Color.Gray.copy(0.5f), RoundedCornerShape(15.dp))
                        ,
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "Add Number",
                            style = MaterialTheme.typography.body1,
                            color = Color.Black
                        )
                    }
                }else{
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .border(
                                1.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(15.dp)
                            )
                            .background(
                                Color.Black.copy(0.5f),
                                RoundedCornerShape(15.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "Add Number",
                            style = MaterialTheme.typography.body1,
                            color = Color.White
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 0.dp)
                .offset(y = 10.dp)
            ,
            contentAlignment = Alignment.Center
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .padding(bottom = 350.dp, start = 10.dp),

                ){

                Text(
                    text = "Make order : ",
                    color = Color.White,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,


                    )
                mainViewModel.addedNumbers.forEach { number ->
                    Text(
                        text = number.num.toString(),
                        color = Color.White,
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp)

                    )

                    Text(
                        text = "<",
                        color = Color.White,
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )

                    if(  mainViewModel.sizeTable()>4)
                    {
                        if(isRunning){
                            isRunning = false

                            insertnbr(ChronometerResult(), db)

                        }
                    }
                }
            }
            Text(text = "----------------------------------------------",
                color = Color.White,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 260.dp, start = 20.dp))

            Text(text = "5 High Score Timing : ",
                color = Color.White,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 200.dp, start = 20.dp))

            Text(text = " Score Timing : ",
                color = Color.White,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 130.dp, start = 20.dp))

            var items = scoreTimingRecord.getTimeAll(db)
            if (items != null) {
                Column (modifier=Modifier.padding(top=120.dp, start = 0.dp)){
                    for (i in 0 until items.size) {
                        var seconds = (items.get(i).time / 1000).toInt()
                        var minutes = seconds / 60


                        var chronoText = String.format("%02d:%02d", minutes % 60, seconds % 60)

                        var j=i+1
                        Text(
                            text ="Score number " +j + " :  "+ chronoText,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }



        }

    }


}

public var isRunning by mutableStateOf(false)
public var chronoText by mutableStateOf("00:00:00")
var startTime = 0L
var elapsedTime = 0L

private fun startChronometer() :Long {
    if (!isRunning) {
        startTime = System.currentTimeMillis() - elapsedTime // Réinitialise startTime
        isRunning = true
        GlobalScope.launch {
            while (isRunning) {
                val currentTime = System.currentTimeMillis()
                elapsedTime = currentTime - startTime
                updateChronoText(elapsedTime)
                delay(1000)
            }
        }
    }
    return elapsedTime;
}

private fun ChronometerResult() :Long {
    if (!isRunning) {
        startTime = System.currentTimeMillis() - elapsedTime // Réinitialise startTime
        GlobalScope.launch {
            while (isRunning) {
                val currentTime = System.currentTimeMillis()
                elapsedTime = currentTime - startTime
                delay(1000)
            }
        }
    }
    return elapsedTime;
}
private fun updateChronoText(elapsedTime: Long) {
    val seconds = (elapsedTime / 1000).toInt()
    val minutes = seconds / 60
    val hours = minutes / 60
    chronoText = String.format("%02d:%02d:%02d", hours, minutes % 60, seconds % 60)

}

private fun insertnbr(elapsedTime: Long,db:BD_Sqlite){

var res=false
    while(res==false){
        val Res: Boolean =
            db.AjoutTime(elapsedTime)
        res=true

        break
    }

}

private  fun getTimeAll(db:BD_Sqlite) {
    val array:ArrayList<TimerScore> ;
    array= db.getAllRecord()!!



}