package com.arifali.simplestopwatchapp.ui.theme

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StopwatchViewModel : ViewModel() {
    private var isRunning = false
    private var timeElapsed = 0L
    private var stopwatchJob: Job? = null

    var minutes = mutableStateOf(0)
    var seconds = mutableStateOf(0)
    var milliseconds = mutableStateOf(0)

    fun startStopwatch() {
        if (!isRunning) {
            isRunning = true
            stopwatchJob = viewModelScope.launch {
                while (isRunning) {
                    delay(10) // update every 10 milliseconds
                   timeElapsed +=60
                    updateTime()
                }
            }
        }
    }

    fun pauseStopwatch() {
        isRunning = false
        stopwatchJob?.cancel()

    }

    fun resetStopwatch() {
        isRunning = false
        stopwatchJob?.cancel()
        timeElapsed = 0L
        updateTime()
    }

    private fun updateTime() {
        minutes.value = (timeElapsed / 60000).toInt()
        seconds.value = ((timeElapsed / 1000) % 60).toInt()
        milliseconds.value = ((timeElapsed % 1000) / 10).toInt()
    }
}