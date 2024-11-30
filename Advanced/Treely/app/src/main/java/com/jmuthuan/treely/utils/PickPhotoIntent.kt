package com.jmuthuan.treely.utils

import android.content.Context
import android.content.Intent

sealed class PickPhotoIntent {
    data class OnPermissionGrantedWith(val compositionContext: Context): Intent()
    object OnPermissionDenied: Intent()
    data class OnImageSavedWith(val compositionContext: Context): Intent()
    object OnImageSavingCanceled: Intent()
}
