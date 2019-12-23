#include <jni.h>
#include <string>
#include <opencv2/opencv.hpp>
#include <ostream>
#include <android/log.h>

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

