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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        GlobalScope.launch(Dispatchers.Main) {
            binding.ivSource.setImageBitmap(bitmap)
            binding.ivBox.setImageBitmap(boxFilter(bitmap))
            binding.ivBlur.setImageBitmap(blurFilter(bitmap))
            binding.ivGaussian.setImageBitmap(gaFilter(bitmap))
            binding.ivMid.setImageBitmap(midFilter(bitmap))
            binding.ivBil.setImageBitmap(bilFilter(bitmap))
            binding.ivMax.setImageBitmap(maxFilter(bitmap))
            binding.ivMin.setImageBitmap(minFilter(bitmap))
            binding.ivFudiao.setImageBitmap(d2Filter(bitmap))
        }
    }

    private suspend fun boxFilter(bitmap: Bitmap): Bitmap = withContext(Dispatchers.Default) {
        val pixel = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(pixel, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        val targetData = mCv.boxFilter(pixel, bitmap.width, bitmap.height)
        val targetBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        targetBitmap.setPixels(targetData, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        return@withContext targetBitmap
    }

    private fun blurFilter(bitmap: Bitmap): Bitmap {
        val pixel = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(pixel, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        val targetData = mCv.blurFilter(pixel, bitmap.width, bitmap.height)
        val targetBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        targetBitmap.setPixels(targetData, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        return targetBitmap
    }

    private suspend fun gaFilter(bitmap: Bitmap): Bitmap = withContext(Dispatchers.Default) {
        val pixel = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(pixel, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        val targetData = mCv.gaussianBlur(pixel, bitmap.width, bitmap.height)
        val targetBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        targetBitmap.setPixels(targetData, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        return@withContext targetBitmap
    }

    private suspend fun midFilter(bitmap: Bitmap): Bitmap = withContext(Dispatchers.Default) {
        val pixel = IntArray(bitmap.height * bitmap.width)
        bitmap.getPixels(pixel, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        val targetData = mCv.midBlur(pixel, bitmap.width, bitmap.height)
        val targetBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        targetBitmap.setPixels(targetData, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        return@withContext targetBitmap
    }

    private suspend fun bilFilter(bitmap: Bitmap): Bitmap = withContext(Dispatchers.Default) {
        val pixel = IntArray(bitmap.height * bitmap.width)
        bitmap.getPixels(pixel, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        val targetData = mCv.bilBlur(pixel, bitmap.width, bitmap.height)
        val targetBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        targetBitmap.setPixels(targetData, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        return@withContext targetBitmap
    }

    private suspend fun maxFilter(bitmap: Bitmap): Bitmap = withContext(Dispatchers.Default) {
        val pixel = IntArray(bitmap.height * bitmap.width)
        bitmap.getPixels(pixel, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        val targetData = mCv.maxFilter(pixel, bitmap.width, bitmap.height)
        val targetBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        targetBitmap.setPixels(targetData, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        return@withContext targetBitmap
    }

    private suspend fun minFilter(bitmap: Bitmap): Bitmap = withContext(Dispatchers.Default) {
        val pixels = IntArray(bitmap.height * bitmap.width)
        bitmap.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        val targetData = mCv.minFilter(pixels, bitmap.width, bitmap.height)
        val targetBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        targetBitmap.setPixels(targetData, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        return@withContext targetBitmap
    }

    private suspend fun d2Filter(bitmap: Bitmap): Bitmap = withContext(Dispatchers.Default) {
        val pixels = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        val targetData = mCv.d2Filter(pixels, bitmap.width, bitmap.height)
        val targetBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        targetBitmap.setPixels(targetData, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        return@withContext targetBitmap
    }
}