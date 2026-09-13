package com.example.simplecalculator.utils

import android.media.ToneGenerator
import android.os.Vibrator

/**
 * Plays a short synthesized retro click tone via [ToneGenerator]. There is no bundled
 * "retro click" audio asset in this project, so the tone is synthesized rather than
 * sampled; a future real-sample swap only needs to touch this function.
 *
 * [ToneGenerator.startTone] can throw [RuntimeException] on some devices/emulators under
 * audio-resource contention -- a failed tone must never crash or block a button press.
 */
fun retroClickSound(toneGenerator: ToneGenerator) {
    try {
        toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP2, 25)
    } catch (e: RuntimeException) {
        // no-op: ToneGenerator can fail to init/play on some devices/emulators;
        // must never crash a button press.
    }
}

/**
 * Single entry point every calculator button's onClick calls for haptic + audio feedback,
 * gated independently by the user's Sound/Vibration settings.
 */
fun performButtonFeedback(
    vibrator: Vibrator,
    toneGenerator: ToneGenerator,
    soundEnabled: Boolean,
    vibrationEnabled: Boolean
) {
    if (vibrationEnabled) vibrationClick(vibrator)
    if (soundEnabled) retroClickSound(toneGenerator)
}
