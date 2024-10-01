package com.arifali.simplestopwatchapp.ui.theme

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arifali.simplestopwatchapp.R
import kotlinx.coroutines.delay

@SuppressLint("DefaultLocale")
@Composable
fun StopwatchScreen(  viewModel: StopwatchViewModel = remember { StopwatchViewModel() }) {
    var rotationDegrees by remember { mutableStateOf(0f) }
    var isRotating by remember { mutableStateOf(false) }
    rememberCoroutineScope()
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(false) }
    var elapsedTime by remember { mutableStateOf(0) }


    LaunchedEffect(isRotating) {
        while (isRotating) {
            delay(100) // Adjust the speed of rotation
            rotationDegrees += 20f // Increment rotation angle
            elapsedTime += 60
            if (elapsedTime % 60000 == 0) {
                playBeep(context)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)

    ) {

        Box(modifier = Modifier.size(280.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ssssssssss),
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp)
                    .graphicsLayer(rotationZ = rotationDegrees)
                    .size(200.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = String.format(
                    "%02d:%02d:%02d",
                    viewModel.minutes.value,
                    viewModel.seconds.value,
                    viewModel.milliseconds.value,

                    ),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(start = 85.dp, top = 130.dp)

            )


        }
        Spacer(modifier = Modifier.height(60.dp))
        Column(
            modifier = Modifier.padding(start = 115.dp, top = 50.dp)
                .wrapContentSize(Alignment.Center),

            ) {
            if (!isPlaying) {
                IconButton(modifier = Modifier.size(64.dp),
                    onClick = {
                        viewModel.startStopwatch()
                        isRotating = true // Start rotation
                        isPlaying = !isPlaying

                    }) {
                    Image(
                        painter = painterResource(id = R.drawable.playbutton),
                        contentDescription = "My Custom Icon",
                        modifier = Modifier.size(60.dp)

                    )

                }
            }

            Row(modifier = Modifier.padding(5.dp)) {
                if (isPlaying) {
                    IconButton(modifier = Modifier.size(64.dp),
                        onClick = {
                            viewModel.pauseStopwatch()
                            isRotating = false // Stop rotation
                            rotationDegrees = 15f
                        }) {

                        Image(
                            painter = painterResource(id = R.drawable.pauseplay),
                            contentDescription = "Pause",
                            modifier = Modifier.size(60.dp),

                            )
                    }

                    IconButton(modifier = Modifier.size(64.dp),
                        onClick = {
                            viewModel.resetStopwatch()
                            isRotating = false // Stop rotation
                            rotationDegrees = 0f // Reset rotation
                            isPlaying = false
                        }) {
                        Image(
                            painter = painterResource(
                                id = R.drawable.reset
                            ),
                            contentDescription = "Reset",
                            modifier = Modifier.size(60.dp),

                            )
                    }

                }


            }

        }
    }

}
private  fun playBeep(context: Context) {
    val mediaPlayer =
        MediaPlayer.create(context,R.raw.single) // Make sure to handle context appropriately
    mediaPlayer.start()
    mediaPlayer.setOnCompletionListener {
        it.release() // Release the MediaPlayer after playback
    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SimpleStopWatchAppTheme{
        StopwatchScreen()
    }
}
