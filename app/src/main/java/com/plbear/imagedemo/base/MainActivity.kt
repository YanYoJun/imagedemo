package com.plbear.imagedemo.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.plbear.imagedemo.R
import com.plbear.imagedemo.databinding.ActivityMainBinding
import com.plbear.imagedemo.filter.SimpleFilterActivity
import com.plbear.imagedemo.gray.GrayActivity

class MainActivity : BaseActivity() {
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.btnGray.setOnClickListener {
            GrayActivity.start(this)
        }
        mBinding.btnSimpleFilter.setOnClickListener {
            SimpleFilterActivity.start(this)
        }
    }
}
