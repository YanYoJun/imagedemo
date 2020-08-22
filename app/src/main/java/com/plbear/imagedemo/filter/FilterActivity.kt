package com.plbear.imagedemo.filter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.plbear.imagedemo.R
import com.plbear.imagedemo.base.BaseActivity
import com.plbear.imagedemo.databinding.ActivityFilterBinding
import com.plbear.imagedemo.jinterface.CvInterface

/**
 * created by yanyongjun on 2020/8/22
 */
class FilterActivity : BaseActivity() {
    companion object {
        fun start(context: Context) {
            val i = Intent(context, FilterActivity::class.java)
            context.startActivity(i)
        }
    }

    private lateinit var binding: ActivityFilterBinding
    private val mCv = CvInterface()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter)
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.to_gray)
        binding.ivSource.setImageBitmap(bitmap)
        binding.ivBox.setImageBitmap(boxFilter(bitmap))
        binding.ivBlur.setImageBitmap(blurFilter(bitmap))
    }

    private fun boxFilter(bitmap: Bitmap): Bitmap {
        val pixel = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(pixel, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        val targetData = mCv.boxFilter(pixel, bitmap.width, bitmap.height)
        val targetBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        targetBitmap.setPixels(targetData, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        return targetBitmap
    }

    private fun blurFilter(bitmap: Bitmap): Bitmap {
        val pixel = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(pixel, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        val targetData = mCv.blurFilter(pixel, bitmap.width, bitmap.height)
        val targetBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        targetBitmap.setPixels(targetData, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        return targetBitmap
    }
}