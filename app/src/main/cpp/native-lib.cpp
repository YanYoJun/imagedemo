#include <jni.h>
#include <string>
#include <opencv2/opencv.hpp>
#include <ostream>

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
