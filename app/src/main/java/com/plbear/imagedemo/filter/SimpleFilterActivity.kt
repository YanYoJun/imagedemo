package com.plbear.imagedemo.filter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.plbear.imagedemo.R
import com.plbear.imagedemo.base.BaseActivity
import com.plbear.imagedemo.databinding.ActivitySimpleFilterBinding
import com.plbear.imagedemo.jinterface.CvInterface

/**
 * created by yanyongjun on 2019-12-23
 */
class SimpleFilterActivity : BaseActivity() {
    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SimpleFilterActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var mBinding: ActivitySimpleFilterBinding
    private val mCv = CvInterface()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_simple_filter)
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.to_gray)
        mBinding.ivSource.setImageBitmap(bitmap)
        mBinding.ivTargetV1.setImageBitmap(binarization(bitmap))
        mBinding.ivReverse.setImageBitmap(reverse(bitmap))
        mBinding.ivRed.setImageBitmap(red(bitmap))
        mBinding.ivOld.setImageBitmap(old(bitmap))
        mBinding.ivZhurong.setImageBitmap(zhurong(bitmap))
        mBinding.ivFreeze.setImageBitmap(freeze(bitmap))
        mBinding.ivChild.setImageBitmap(child(bitmap))
    }

    /**
     * 二值化处理
     */
    private fun binarization(bitmap: Bitmap): Bitmap {
        val piexl = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(piexl, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        val targetData = mCv.binarization(piexl, bitmap.width, bitmap.height, 110)
        val targetBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        targetBitmap.setPixels(targetData, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        return targetBitmap
    }

    private fun reverse(bitmap: Bitmap): Bitmap {
        val piexl = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(piexl, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        val targetData = mCv.reverse(piexl, bitmap.width, bitmap.height)
        val targetBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        targetBitmap.setPixels(targetData, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        return targetBitmap
    }

    private fun red(bitmap: Bitmap): Bitmap {
        val piexl = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(piexl, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        val targetData = mCv.red(piexl, bitmap.width, bitmap.height)
        val targetBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        targetBitmap.setPixels(targetData, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        return targetBitmap
    }

    private fun old(bitmap: Bitmap): Bitmap {
        val piexl = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(piexl, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        val targetData = mCv.old(piexl, bitmap.width, bitmap.height)
        val targetBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        targetBitmap.setPixels(targetData, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        return targetBitmap
    }

    private fun zhurong(bitmap: Bitmap): Bitmap {
        val piexl = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(piexl, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        val targetData = mCv.zhurong(piexl, bitmap.width, bitmap.height)
        val targetBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        targetBitmap.setPixels(targetData, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        return targetBitmap
    }

    private fun freeze(bitmap: Bitmap): Bitmap {
        val piexl = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(piexl, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        val targetData = mCv.freeze(piexl, bitmap.width, bitmap.height)
        val targetBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        targetBitmap.setPixels(targetData, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        return targetBitmap
    }

    private fun child(bitmap: Bitmap): Bitmap {
        val piexl = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(piexl, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        val targetData = mCv.child(piexl, bitmap.width, bitmap.height)
        val targetBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        targetBitmap.setPixels(targetData, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        return targetBitmap
    }
}