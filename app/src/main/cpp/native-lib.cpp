#include <jni.h>
#include <string>
#include <opencv2/opencv.hpp>
#include <ostream>
#include <android/log.h>
#include <math.h>

#define LOG_TAG    "imlog"
#define LOGD(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

using namespace cv;
using namespace std;


extern "C" JNIEXPORT jstring JNICALL
Java_com_plbear_imagedemo_jinterface_CvInterface_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jintArray JNICALL
Java_com_plbear_imagedemo_jinterface_CvInterface_bitmap2Grey(
        JNIEnv *env,
        jobject /* this */, jintArray buf, jint w, jint h) {
    jint *cbuf;
    jboolean ptfalse = false;
    cbuf = env->GetIntArrayElements(buf, &ptfalse);
    if (cbuf == NULL) {
        return 0;
    }

    Mat imgData(h, w, CV_8UC4, (unsigned char *) cbuf);
// 注意，Android的Bitmap是ARGB四通道,而不是RGB三通道
    cvtColor(imgData, imgData, COLOR_BGRA2GRAY);
    cvtColor(imgData, imgData, COLOR_GRAY2BGRA);

    int size = w * h;
    jintArray result = env->NewIntArray(size);
    env->SetIntArrayRegion(result, 0, size, (jint *) imgData.data);
    env->ReleaseIntArrayElements(buf, cbuf, 0);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL
Java_com_plbear_imagedemo_jinterface_CvInterface_bitmap2GreyV2(
        JNIEnv *env,
        jobject, jintArray buf, jint w, jint h) {
    jint *cbuf;
    jboolean ptfalse = false;
    cbuf = env->GetIntArrayElements(buf, &ptfalse);
    if (cbuf == NULL) {
        return 0;
    }
    Mat imgData(h, w, CV_8UC4, (unsigned char *) cbuf);
    int nr = imgData.rows; //行数
    int channel = imgData.channels();
    LOGD("print channel,%d", channel);
    int nc = imgData.cols; //每一行的像素值
    for (int i = 0; i < nr; ++i) {
        auto rowData = imgData.ptr<unsigned char>(i);
        for (int j = 0; j < nc; ++j) {
            unsigned char *curData = rowData + (j * channel);
            //平均值灰度算法
            int total = (int) curData[0] + (int) curData[1] + (int) curData[2];
            auto avg = (unsigned char) (total / 3);
            curData[0] = avg;
            curData[1] = avg;
            curData[2] = avg;
        }
    }
    int size = w * h;
    jintArray result = env->NewIntArray(size);
    env->SetIntArrayRegion(result, 0, size, (jint *) imgData.data);
    env->ReleaseIntArrayElements(buf, cbuf, 0);
    return result;
}

extern "C" JNIEXPORT jintArray JNICALL
Java_com_plbear_imagedemo_jinterface_CvInterface_bitmap2GreyV3(
        JNIEnv *env,
        jobject, jintArray buf, jint w, jint h) {
    jint *cbuf;
    jboolean ptfalse = false;
    cbuf = env->GetIntArrayElements(buf, &ptfalse);
    if (cbuf == NULL) {
        return 0;
    }
    Mat imgData(h, w, CV_8UC4, (unsigned char *) cbuf);
    int nr = imgData.rows;
    int channel = imgData.channels();
    int nc = imgData.cols;
    for (int i = 0; i < nr; i++) {
        auto rowData = imgData.ptr<unsigned char>(i);
        for (int j = 0; j < nc; j++) {
            unsigned char *curData = rowData + (j * channel);
            auto blue = curData[0];
            auto green = curData[1];
            auto red = curData[2];
            auto avg = (unsigned char) (blue * 0.11 + green * 0.59 + red * 0.3);
            curData[0] = avg;
            curData[1] = avg;
            curData[2] = avg;
        }
    }
    int size = w * h;
    jintArray result = env->NewIntArray(size);
    env->SetIntArrayRegion(result, 0, size, (jint *) imgData.data);
    env->ReleaseIntArrayElements(buf, cbuf, 0);
    return result;
}

//external fun binarization(pixels: IntArray, w: Int, h: Int, threshold: Int): IntArray
extern "C" JNIEXPORT jintArray JNICALL
Java_com_plbear_imagedemo_jinterface_CvInterface_binarization(
        JNIEnv *env,
        jobject, jintArray buf, jint w, jint h, jint threshold) {
    jint *cbuf;
    jboolean ptfalse = false;
    cbuf = env->GetIntArrayElements(buf, &ptfalse);
    if (cbuf == NULL) {
        return 0;
    }
    Mat imgData(h, w, CV_8UC4, (unsigned char *) cbuf);
    int nr = imgData.rows;
    int channel = imgData.channels();
    int nc = imgData.cols;
    for (int i = 0; i < nr; ++i) {
        auto rowData = imgData.ptr<unsigned char>(i);
        for (int j = 0; j < nc; j++) {
            unsigned char *curData = rowData + (j * channel);
            auto blue = curData[0];
            auto green = curData[1];
            auto red = curData[2];
            auto gray = (unsigned char) (blue * 0.11 + green * 0.59 + red * 0.3);
            if (gray > threshold) {
                curData[0] = curData[1] = curData[2] = 255;
            } else {
                curData[0] = curData[1] = curData[2] = 0;
            }
        }
    }
    int size = w * h;
    jintArray result = env->NewIntArray(size);
    env->SetIntArrayRegion(result, 0, size, (jint *) imgData.data);
    env->ReleaseIntArrayElements(buf, cbuf, 0);
    return result;
}

//反向滤镜
extern "C" JNIEXPORT jintArray JNICALL
Java_com_plbear_imagedemo_jinterface_CvInterface_reverse(
        JNIEnv *env,
        jobject, jintArray buf, jint w, jint h) {
    jint *cbuf;
    jboolean ptfalse = false;
    cbuf = env->GetIntArrayElements(buf, &ptfalse);
    if (cbuf == NULL) {
        return 0;
    }
    Mat imgData(h, w, CV_8UC4, (unsigned char *) cbuf);
    int nr = imgData.rows;
    int channel = imgData.channels();
    int nc = imgData.cols;
    for (int i = 0; i < nr; i++) {
        auto rowData = imgData.ptr<unsigned char>(i);
        for (int j = 0; j < nc; j++) {
            unsigned char *curData = rowData + (j * channel);
            curData[0] = (unsigned char) (255 - curData[0]);
            curData[1] = (unsigned char) (255 - curData[1]);
            curData[2] = (unsigned char) (255 - curData[2]);
        }
    }
    int size = w * h;
    jintArray result = env->NewIntArray(size);
    env->SetIntArrayRegion(result, 0, size, (jint *) imgData.data);
    env->ReleaseIntArrayElements(buf, cbuf, 0);
    return result;
}

//单色
extern "C" JNIEXPORT jintArray JNICALL
Java_com_plbear_imagedemo_jinterface_CvInterface_red(
        JNIEnv *env,
        jobject, jintArray buf, jint w, jint h) {
    jint *cbuf;
    jboolean ptfalse = false;
    cbuf = env->GetIntArrayElements(buf, &ptfalse);
    if (cbuf == NULL) {
        return 0;
    }
    Mat imgData(h, w, CV_8UC4, (unsigned char *) cbuf);
    int nr = imgData.rows;
    int channel = imgData.channels();
    int nc = imgData.cols;
    for (int i = 0; i < nr; i++) {
        auto rowData = imgData.ptr<unsigned char>(i);
        for (int j = 0; j < nc; j++) {
            unsigned char *curData = rowData + (j * channel);
            curData[0] = 0;
            curData[1] = 0;
        }
    }
    int size = w * h;
    jintArray result = env->NewIntArray(size);
    env->SetIntArrayRegion(result, 0, size, (jint *) imgData.data);
    env->ReleaseIntArrayElements(buf, cbuf, 0);
    return result;
}

//怀旧滤镜
extern "C" JNIEXPORT jintArray JNICALL
Java_com_plbear_imagedemo_jinterface_CvInterface_old(
        JNIEnv *env,
        jobject, jintArray buf, jint w, jint h) {
    jint *cbuf;
    jboolean ptfalse = false;
    cbuf = env->GetIntArrayElements(buf, &ptfalse);
    if (cbuf == NULL) {
        return 0;
    }
    Mat imgData(h, w, CV_8UC4, (unsigned char *) cbuf);
    int nr = imgData.rows;
    int channel = imgData.channels();
    int nc = imgData.cols;
    for (int i = 0; i < nr; i++) {
        auto rowData = imgData.ptr<unsigned char>(i);
        for (int j = 0; j < nc; j++) {
            unsigned char *curData = rowData + (j * channel);
            auto blue = curData[0];
            auto green = curData[1];
            auto red = curData[2];
            auto DBlue = (0.272 * red + 0.534 * green + 0.131 * blue);
            auto DGreen = (0.349 * red + 0.686 * green + 0.168 * blue);
            auto DRed = (0.393 * red + 0.769 * green + 0.189 * blue);
            curData[0] = (unsigned char) (DBlue > 255 ? 255 : DBlue);
            curData[1] = (unsigned char) (DGreen > 255 ? 255 : DGreen);
            curData[2] = (unsigned char) (DRed > 255 ? 255 : DRed);
        }
    }
    int size = w * h;
    jintArray result = env->NewIntArray(size);
    env->SetIntArrayRegion(result, 0, size, (jint *) imgData.data);
    env->ReleaseIntArrayElements(buf, cbuf, 0);
    return result;
}


//铸熔滤镜
extern "C" JNIEXPORT jintArray JNICALL
Java_com_plbear_imagedemo_jinterface_CvInterface_zhurong(
        JNIEnv *env,
        jobject, jintArray buf, jint w, jint h) {
    jint *cbuf;
    jboolean ptfalse = false;
    cbuf = env->GetIntArrayElements(buf, &ptfalse);
    if (cbuf == NULL) {
        return 0;
    }
    Mat imgData(h, w, CV_8UC4, (unsigned char *) cbuf);
    int nr = imgData.rows;
    int channel = imgData.channels();
    int nc = imgData.cols;
    for (int i = 0; i < nr; i++) {
        auto rowData = imgData.ptr<unsigned char>(i);
        for (int j = 0; j < nc; j++) {
            unsigned char *curData = rowData + (j * channel);
            auto blue = curData[0];
            auto green = curData[1];
            auto red = curData[2];
            auto DBlue = 128.0 * blue / (green + red + 1);
            auto DGreen = 128.0 * green / (red + blue + 1);
            auto DRed = 128.0 * red / (green + blue + 1);
            curData[0] = (unsigned char) (DBlue > 255 ? 255 : DBlue);
            curData[1] = (unsigned char) (DGreen > 255 ? 255 : DGreen);
            curData[2] = (unsigned char) (DRed > 255 ? 255 : DRed);
        }
    }
    int size = w * h;
    jintArray result = env->NewIntArray(size);
    env->SetIntArrayRegion(result, 0, size, (jint *) imgData.data);
    env->ReleaseIntArrayElements(buf, cbuf, 0);
    return result;
}

//冰冻滤镜
extern "C" JNIEXPORT jintArray JNICALL
Java_com_plbear_imagedemo_jinterface_CvInterface_freeze(
        JNIEnv *env,
        jobject, jintArray buf, jint w, jint h) {
    jint *cbuf;
    jboolean ptfalse = false;
    cbuf = env->GetIntArrayElements(buf, &ptfalse);
    if (cbuf == NULL) {
        return 0;
    }
    Mat imgData(h, w, CV_8UC4, (unsigned char *) cbuf);
    int nr = imgData.rows;
    int channel = imgData.channels();
    int nc = imgData.cols;
    for (int i = 0; i < nr; i++) {
        auto rowData = imgData.ptr<unsigned char>(i);
        for (int j = 0; j < nc; j++) {
            unsigned char *curData = rowData + (j * channel);
            auto blue = curData[0];
            auto green = curData[1];
            auto red = curData[2];
            auto DBlue = 3.0 * (blue - red - green) / 2;
            auto DGreen = 3.0 * (green - blue - red) / 2;
            auto DRed = 3.0 * (red - blue - green) / 2;
            curData[0] = (unsigned char) (DBlue > 255 ? 255 : (DBlue < 0 ? 0 : DBlue));
            curData[1] = (unsigned char) (DGreen > 255 ? 255 : (DGreen < 0 ? 0 : DGreen));
            curData[2] = (unsigned char) (DRed > 255 ? 255 : (DRed < 0 ? 0 : DRed));
        }
    }
    int size = w * h;
    jintArray result = env->NewIntArray(size);
    env->SetIntArrayRegion(result, 0, size, (jint *) imgData.data);
    env->ReleaseIntArrayElements(buf, cbuf, 0);
    return result;
}

//连环画滤镜
extern "C" JNIEXPORT jintArray JNICALL
Java_com_plbear_imagedemo_jinterface_CvInterface_child(
        JNIEnv *env,
        jobject, jintArray buf, jint w, jint h) {
    jint *cbuf;
    jboolean ptfalse = false;
    cbuf = env->GetIntArrayElements(buf, &ptfalse);
    if (cbuf == NULL) {
        return 0;
    }
    Mat imgData(h, w, CV_8UC4, (unsigned char *) cbuf);
    int nr = imgData.rows;
    int channel = imgData.channels();
    int nc = imgData.cols;
    for (int i = 0; i < nr; i++) {
        auto rowData = imgData.ptr<unsigned char>(i);
        for (int j = 0; j < nc; j++) {
            unsigned char *curData = rowData + (j * channel);
            auto blue = curData[0];
            auto green = curData[1];
            auto red = curData[2];
            auto DBlue = abs(1.0 * blue - green + blue + red) * green / 256;
            auto DGreen = abs(1.0 * blue - green + blue + red) * red / 256;
            auto DRed = abs(1.0 * green - blue + green + red) * red / 256;
            curData[0] = (unsigned char) DBlue;
            curData[1] = (unsigned char) DGreen;
            curData[2] = (unsigned char) DRed;
        }
    }
    int size = w * h;
    jintArray result = env->NewIntArray(size);
    env->SetIntArrayRegion(result, 0, size, (jint *) imgData.data);
    env->ReleaseIntArrayElements(buf, cbuf, 0);
    return result;
}

//方框滤波
extern "C" JNIEXPORT jintArray JNICALL
Java_com_plbear_imagedemo_jinterface_CvInterface_boxFilter(
        JNIEnv *env,
        jobject CvInterface, jintArray buf, jint w, jint h) {
    jboolean ptflase = false;
    jint *cbuf = env->GetIntArrayElements(buf, &ptflase);
    if (cbuf == NULL) {
        return 0;
    }
    Mat imgData(h, w, CV_8UC4, (unsigned char *) cbuf);
    boxFilter(imgData, imgData, -1, Size(2, 2), Point(-1, -1), false);
    int size = w * h;
    jintArray result = env->NewIntArray(size);
    env->SetIntArrayRegion(result, 0, size, (jint *) imgData.data);
    env->ReleaseIntArrayElements(buf, cbuf, 0);
    return result;
}

//均值滤波
extern "C"
JNIEXPORT jintArray JNICALL
Java_com_plbear_imagedemo_jinterface_CvInterface_blurFilter(JNIEnv *env, jobject thiz,
                                                            jintArray pixels, jint w, jint h) {
    jboolean temp = JNI_FALSE;
    jint *cbuf = env->GetIntArrayElements(pixels, &temp);
    if (cbuf == NULL) {
        return 0;
    }
    Mat imgData(h, w, CV_8UC4, (unsigned char *) cbuf);
    blur(imgData, imgData, Size(30, 30));
    int size = w * h;
    jintArray result = env->NewIntArray(size);
    //这一部分其实作用不大, 因为下面的代码已经完成的pixels的数据改变, 所以其实没有必要拿一个新数组返回结果
    env->SetIntArrayRegion(result, 0, size, (jint *) imgData.data);
    env->ReleaseIntArrayElements(pixels, cbuf, 0);
    return result;
}

//高斯滤波
extern "C"
JNIEXPORT jintArray JNICALL
Java_com_plbear_imagedemo_jinterface_CvInterface_gaussianBlur(JNIEnv *env, jobject thiz,
                                                              jintArray pixels, jint width,
                                                              jint height) {
    jboolean isCopy = JNI_FALSE;
    jint *buf = env->GetIntArrayElements(pixels, &isCopy);
    if (buf == NULL) return 0;
    Mat imgData(height, width, CV_8UC4, (unsigned char *) buf);
    GaussianBlur(imgData, imgData, Size(31, 31), 0);
//    blur(imgData, imgData, Size(30, 30));
    int size = width * height;
    jintArray result = env->NewIntArray(size);
    env->SetIntArrayRegion(result, 0, size, (jint *) imgData.data);
    env->ReleaseIntArrayElements(pixels, buf, 0);
    return result;
}

//中值滤波
extern "C"
JNIEXPORT jintArray JNICALL
Java_com_plbear_imagedemo_jinterface_CvInterface_midBlur(JNIEnv *env, jobject thiz,
                                                         jintArray pixels, jint width,
                                                         jint height) {
    jboolean isCopy = JNI_FALSE;
    jint *buf = env->GetIntArrayElements(pixels, &isCopy);
    if (buf == NULL) return 0;
    Mat imgData(height, width, CV_8UC4, buf);
    medianBlur(imgData, imgData, 31);
//    blur(imgData, imgData, Size(30, 30));
    int size = width * height;
    jintArray result = env->NewIntArray(size);
    env->SetIntArrayRegion(result, 0, size, (jint *) imgData.data);
    env->ReleaseIntArrayElements(pixels, buf, 0);
    return result;
}

extern "C"
JNIEXPORT jintArray JNICALL
Java_com_plbear_imagedemo_jinterface_CvInterface_bilBlur(JNIEnv *env, jobject thiz,
                                                         jintArray pixels, jint width,
                                                         jint height) {
    jboolean isCopy = JNI_FALSE;
    jint *buf = env->GetIntArrayElements(pixels, &isCopy);
    if (buf == NULL) return 0;
    Mat imgData(height, width, CV_8UC3, buf);
    Mat outData(height, width,CV_8UC4);
    bilateralFilter(imgData, outData, 10, 10 * 2, 10 / 2);
    int size = width * height;
    jintArray result = env->NewIntArray(size);
    env->SetIntArrayRegion(result, 0, size, (jint *) outData.data);
    env->ReleaseIntArrayElements(pixels, buf, 0);
    return result;
}