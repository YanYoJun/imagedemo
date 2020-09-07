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
    external fun bitmap2GreyV2(pixels: IntArray, w: Int, h: Int): IntArray
    external fun bitmap2GreyV3(pixels: IntArray, w: Int, h: Int): IntArray
    external fun binarization(pixels: IntArray, w: Int, h: Int, threshold: Int): IntArray
    external fun reverse(pixels: IntArray, w: Int, h: Int): IntArray
    external fun red(pixels: IntArray, w: Int, h: Int): IntArray
    external fun old(pixels: IntArray, w: Int, h: Int): IntArray
    external fun zhurong(pixels: IntArray, w: Int, h: Int): IntArray
    external fun freeze(pixels: IntArray, w: Int, h: Int): IntArray
    external fun child(pixels: IntArray, w: Int, h: Int): IntArray
    external fun boxFilter(pixels: IntArray, w: Int, h: Int): IntArray
    external fun blurFilter(pixels: IntArray, w: Int, h: Int): IntArray
    external fun gaussianBlur(pixels: IntArray, width: Int, height: Int): IntArray
    external fun midBlur(pixels: IntArray, width: Int, height: Int): IntArray
    external fun bilBlur(pixels: IntArray, width: Int, height: Int): IntArray
    external fun light(pixels: IntArray, width: Int, height: Int, light: Int,ratio:Float): IntArray
}