package com.plbear.imagedemo.jinterface

/**
 * created by yanyongjun on 2019-12-21
 */
class CvInterface {
    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }

    external fun stringFromJNI(): String
    external fun bitmap2Grey(pixels: IntArray, w: Int, h: Int): IntArray
}