package com.plbear.imagedemo.utils

import android.util.Log

/**
 * created by yanyongjun on 2019-12-21
 */
fun logcat(str: String?) {
    if(str == null){
        Log.e("imlog","null")
    }
    Log.e("imlog", str)
}