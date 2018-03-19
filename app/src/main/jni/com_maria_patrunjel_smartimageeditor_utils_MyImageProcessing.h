/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
#include <stdio.h>
#include <opencv2/opencv.hpp>
#include "opencv2/photo.hpp"
#include "opencv2/imgproc.hpp"
#include "opencv2/highgui.hpp"
#include "opencv2/core.hpp"
#include <stdlib.h>

using namespace cv;
using namespace std;

#ifndef _Included_com_maria_patrunjel_smartimageeditor_MyImageProcessing
#define _Included_com_maria_patrunjel_smartimageeditor_MyImageProcessing
#ifdef __cplusplus
#define JNICALL
extern "C" {
#endif
/*
 * Class:     com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing
 * Method:    changeRGBChannels
 * Signature: (JJIII)V
 */
JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing_changeRGBChannels
  (JNIEnv *, jclass, jlong, jlong, jint, jint, jint);

/*
 * Class:     com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing
 * Method:    gammaCorrection
 * Signature: (JJF)V
 */
JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing_gammaCorrection
  (JNIEnv *, jclass, jlong, jlong, jfloat);

/*
 * Class:     com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing
 * Method:    sepiaFilter
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing_sepiaFilter
  (JNIEnv *, jclass, jlong, jlong);

/*
 * Class:     com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing
 * Method:    cartoonFilter
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing_cartoonFilter
  (JNIEnv *, jclass, jlong, jlong);

/*
 * Class:     com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing
 * Method:    sketchFilter
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing_sketchFilter
  (JNIEnv *, jclass, jlong, jlong);

#ifdef __cplusplus
}
#endif
#endif
