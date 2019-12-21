package com.plbear.imagedemo.base

import android.app.Application

/**
 * created by yanyongjun on 2019-12-21
 */
class BaseApp : Application() {
    companion object {
        private var sInstance: Application? = null
        fun instance(): Application {
            return sInstance!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        sInstance = this
    }
}