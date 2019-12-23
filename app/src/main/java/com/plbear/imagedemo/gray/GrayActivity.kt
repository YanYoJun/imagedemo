package com.plbear.imagedemo.gray

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.plbear.imagedemo.R
import com.plbear.imagedemo.base.BaseActivity
import com.plbear.imagedemo.databinding.ActivityGrayBinding
import com.plbear.imagedemo.jinterface.CvInterface

/**
 * 将图像置灰处理
 * created by yanyongjun on 2019-12-21
 */
class GrayActivity : BaseActivity() {
    companion object {
        fun start(context: Context) {
            val intent = Intent(context, GrayActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var mBinding: ActivityGrayBinding
    private val mCv = CvInterface()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_gray)
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.to_gray)
        mBinding.ivSource.setImageBitmap(bitmap)
        mBinding.ivTarget.setImageBitmap(gray(bitmap))
        mBinding.ivTargetV2.setImageBitmap(grayV2(bitmap))
        mBinding.ivTargetV3.setImageBitmap(grayV3(bitmap))
    }

    /**
     * cvColor 通道模式转换
     */
    private fun gray(bitmap: Bitmap): Bitmap {
        val piexl = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(piexl, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        val targetData = mCv.bitmap2Grey(piexl, bitmap.width, bitmap.height)
        val targetBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        targetBitmap.setPixels(targetData, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        return targetBitmap
    }

    /**
     * 均值灰度
     */
    private fun grayV2(bitmap: Bitmap): Bitmap {
        val piexl = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(piexl, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        val targetData = mCv.bitmap2GreyV2(piexl, bitmap.width, bitmap.height)
        val targetBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        targetBitmap.setPixels(targetData, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        return targetBitmap
    }

    /**
     * 灰度自实现
     */
    private fun grayV3(bitmap: Bitmap): Bitmap {
        val piexl = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(piexl, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        val targetData = mCv.bitmap2GreyV3(piexl, bitmap.width, bitmap.height)
        val targetBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        targetBitmap.setPixels(targetData, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        return targetBitmap
    }
}