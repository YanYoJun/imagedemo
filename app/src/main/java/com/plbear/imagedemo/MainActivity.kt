package com.plbear.imagedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Example of a call to a native method
        sample_text.text = stringFromJNI()
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    external fun Bitmap2Grey(pixels: IntArray, w: Int, h: Int): IntArray

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }


//    public void gray(){
//        int w = bitmap.getWidth();
//        int h = bitmap.getHeight();
//        int[] piexls = new int[w*h];
//        bitmap.getPixels(piexls,0,w,0,0,w,h);
//        int[] resultData =Bitmap2Grey(piexls,w,h);
//        Bitmap resultImage = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
//        resultImage.setPixels(resultData,0,w,0,0,w,h);
//        imageView.setImageBitmap(resultImage);
//    }
}
