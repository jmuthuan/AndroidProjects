package com.example.simplecalculator.utils

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

fun vibrationClick(vibrator: Vibrator) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(
            VibrationEffect.createOneShot(
                150L,
                VibrationEffect.DEFAULT_AMPLITUDE
            )
        )
    } else {
        vibrator.vibrate(150)
    }
}