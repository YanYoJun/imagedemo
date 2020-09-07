package com.plbear.imagedemo.gray

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import com.plbear.imagedemo.R
import com.plbear.imagedemo.base.BaseActivity
import com.plbear.imagedemo.databinding.ActivityLightBinding
import com.plbear.imagedemo.jinterface.CvInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * created by yanyongjun on 2020/8/23
 */
class LightActivity : BaseActivity() {
    companion object {
        fun start(context: Context) {
            val i = Intent(context, LightActivity::class.java)
            context.startActivity(i)
        }
    }

    private lateinit var binding: ActivityLightBinding
    private val mCv = CvInterface()
    private val listener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            onChange()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }
    }

    private val bitmap by lazy {
        BitmapFactory.decodeResource(resources, R.drawable.to_gray)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_light)
        binding.seekBarDuibi.setOnSeekBarChangeListener(listener)
//        GlobalScope.launch(Dispatchers.Main) {
//            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.to_gray)
//            binding.ivSource.setImageBitmap(light(bitmap, 0, 1.5f))
//        }
        binding.seekBarLight.setOnSeekBarChangeListener(listener)
        binding.seekBarDuibi.max = 3000
        binding.seekBarDuibi.min = 0
        binding.seekBarLight.max = 2550
        binding.seekBarLight.min = -2550
        binding.seekBarDuibi.progress = 1000
        binding.seekBarLight.progress = 0
        onChange()
    }

    private fun onChange() {
        GlobalScope.launch(Dispatchers.Main) {
            binding.ivSource.setImageBitmap(light(bitmap, binding.seekBarLight.progress/10, binding.seekBarDuibi.progress.toFloat() / 1000))
        }
    }

    private suspend fun light(bitmap: Bitmap, light: Int, ratio: Float): Bitmap =
        withContext(Dispatchers.Default) {
            val pixel = IntArray(bitmap.width * bitmap.height)
            bitmap.getPixels(pixel, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
            val targetData = mCv.light(pixel, bitmap.width, bitmap.height, light, ratio)
            val targetBitmap =
                Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
            targetBitmap.setPixels(targetData, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
            return@withContext targetBitmap
        }
}