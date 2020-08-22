package com.plbear.imagedemo.utils

import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * created by yanyongjun on 2020/8/22
 */
@RequiresApi(Build.VERSION_CODES.Q)
fun Bitmap.print() {
    val builder = StringBuilder()
    for (i in 0 until this.width) {
        for (j in 0 until this.height) {
            builder.append(this.getPixel(i,j))
            builder.append(" ")
        }
        builder.append("\n")
    }
    logcat(builder.toString())
}