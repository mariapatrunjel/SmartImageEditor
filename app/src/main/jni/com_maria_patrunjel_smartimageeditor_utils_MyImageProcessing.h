#include <jni.h>
#include <stdio.h>
#include <math.h>
#include <opencv2/opencv.hpp>
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

/*
 * Class:     com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing
 * Method:    colorMapFilter
 * Signature: (JJI)V
 */
JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing_colorMapFilter
  (JNIEnv *, jclass, jlong, jlong, jint);

/*
 * Class:     com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing
 * Method:    histogramEqualizationYCbCr
 * Signature: (JJI)V
 */
JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing_histogramEqualizationYCbCr
  (JNIEnv *, jclass, jlong, jlong, jint);

/*
 * Class:     com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing
 * Method:    histogramEqualizationHSV
 * Signature: (JJI)V
 */
JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing_histogramEqualizationHSV
  (JNIEnv *, jclass, jlong, jlong, jint);

/*
 * Class:     com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing
 * Method:    redTonedFillter
 * Signature: (JJD)V
 */
JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing_redTonedFillter
  (JNIEnv *, jclass, jlong, jlong, jdouble);

/*
 * Class:     com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing
 * Method:    greenTonedFillter
 * Signature: (JJD)V
 */
JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing_greenTonedFillter
  (JNIEnv *, jclass, jlong, jlong, jdouble);

/*
 * Class:     com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing
 * Method:    blueTonedFillter
 * Signature: (JJD)V
 */
JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing_blueTonedFillter
  (JNIEnv *, jclass, jlong, jlong, jdouble);

/*
 * Class:     com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing
 * Method:    interpolate
 * Signature: (JJD)V
 */
JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing_interpolate
  (JNIEnv *, jclass, jlong, jlong, jdouble);

#ifdef __cplusplus
}
#endif
#endif
