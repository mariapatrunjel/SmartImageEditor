#include "com_maria_patrunjel_smartimageeditor_MyImageProcessing.h"
JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_MyImageProcessing_darkFilter
  (JNIEnv *, jclass, jlong addrRgba, jlong addrResultImage)
{
    Mat & mRgba = *(Mat*) addrRgba;
    Mat & resultImage = *(Mat *)addrResultImage;

    for(int i=0; i<mRgba.rows; i++){
        for(int j=0; j<mRgba.cols; j++){
                resultImage.data[resultImage.step[0]*i + resultImage.step[1]* j + 0] =  mRgba.data[mRgba.step[0]*i + mRgba.step[1]* j + 0]/2;
                resultImage.data[resultImage.step[0]*i + resultImage.step[1]* j + 1] =  mRgba.data[mRgba.step[0]*i + mRgba.step[1]* j + 1]/2;
                resultImage.data[resultImage.step[0]*i + resultImage.step[1]* j + 2] =  mRgba.data[mRgba.step[0]*i + mRgba.step[1]* j + 2]/2;
        }
    }
}

JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_MyImageProcessing_brightFilter
        (JNIEnv *, jclass, jlong addrRgba, jlong addrResultImage)
{
    Mat & mRgba = *(Mat*) addrRgba;
    Mat & resultImage = *(Mat *)addrResultImage;

    for(int i=0; i<mRgba.rows; i++){
        for(int j=0; j<mRgba.cols; j++){
            if(mRgba.data[mRgba.step[0]*i + mRgba.step[1]* j + 0]*2<256)
                resultImage.data[resultImage.step[0]*i + resultImage.step[1]* j + 0] =  mRgba.data[mRgba.step[0]*i + mRgba.step[1]* j + 0]*2;
            else
                resultImage.data[resultImage.step[0]*i + resultImage.step[1]* j + 0] = 255;
            if(mRgba.data[mRgba.step[0]*i + mRgba.step[1]* j + 1]*2<256)
                resultImage.data[resultImage.step[0]*i + resultImage.step[1]* j + 1] =  mRgba.data[mRgba.step[0]*i + mRgba.step[1]* j + 1]*2;
            else
                resultImage.data[resultImage.step[0]*i + resultImage.step[1]* j + 1] = 255;
            if(mRgba.data[mRgba.step[0]*i + mRgba.step[1]* j + 2]*2<256)
                resultImage.data[resultImage.step[0]*i + resultImage.step[1]* j + 2] =  mRgba.data[mRgba.step[0]*i + mRgba.step[1]* j + 2]*2;
            else
                resultImage.data[resultImage.step[0]*i + resultImage.step[1]* j + 2] = 255;
        }
    }
}

JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_MyImageProcessing_redFilter
        (JNIEnv *, jclass, jlong addrRgba, jlong addrResultImage)
{
    Mat &mRgba = *(Mat *) addrRgba;
    Mat &resultImage = *(Mat *) addrResultImage;

    for (int i = 0; i < mRgba.rows; i++) {
        for (int j = 0; j < mRgba.cols; j++) {
            if (mRgba.data[mRgba.step[0] * i + mRgba.step[1] * j + 0] * 2 < 256)
                resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 0] = mRgba.data[mRgba.step[0] * i + mRgba.step[1] * j + 0] * 2;
            else
                resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 0] = 255;
            resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 1] = 0;
            resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 2] = 0;
        }
    }
}

JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_MyImageProcessing_greenFilter
    (JNIEnv *, jclass, jlong addrRgba, jlong addrResultImage)
{
    Mat &mRgba = *(Mat *) addrRgba;
    Mat &resultImage = *(Mat *) addrResultImage;

    for (int i = 0; i < mRgba.rows; i++) {
        for (int j = 0; j < mRgba.cols; j++) {
            resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 0] = 0;
            if (mRgba.data[mRgba.step[0] * i + mRgba.step[1] * j + 1] * 2 < 256)
                resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 1] = mRgba.data[mRgba.step[0] * i + mRgba.step[1] * j + 1] * 2;
            else
                resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 1] = 255;
            resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 2] = 0;
        }
    }
}
JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_MyImageProcessing_blueFilter
        (JNIEnv *, jclass, jlong addrRgba, jlong addrResultImage)
{
    Mat &mRgba = *(Mat *) addrRgba;
    Mat &resultImage = *(Mat *) addrResultImage;

    for (int i = 0; i < mRgba.rows; i++) {
        for (int j = 0; j < mRgba.cols; j++) {
            resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 0] = 0;
            resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 1] = 0;
            if (mRgba.data[mRgba.step[0] * i + mRgba.step[1] * j + 2] * 2 < 256)
                resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 2] = mRgba.data[mRgba.step[0] * i + mRgba.step[1] * j + 2] * 2;
            else
                resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 2] = 255;
        }
    }
}

JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_MyImageProcessing_binarizationFilter
        (JNIEnv *, jclass, jlong addrRgba, jlong addrResultImage)
{
    Mat &mRgba = *(Mat *) addrRgba;
    Mat &resultImage = *(Mat *) addrResultImage;
    cv::cvtColor(mRgba, resultImage, CV_BGR2GRAY);

    for (int i = 0; i < mRgba.rows; i++) {
        for (int j = 0; j < mRgba.cols; j++) {
            uchar intensity = resultImage.at<uchar>(i, j);
            if (intensity < 128) {
                resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 0] = 0;
            }
            else {
                resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 0] = 255;
            }

        }
    }
}

JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_MyImageProcessing_changeRGBChannels
        (JNIEnv *, jclass, jlong addrRgba, jlong addrResultImage,jint red,jint green,jint blue)
{
    Mat &mRgba = *(Mat *) addrRgba;
    Mat &resultImage = *(Mat *) addrResultImage;
    for (int i = 0; i < mRgba.rows; i++) {
        for (int j = 0; j < mRgba.cols; j++) {
            int valueRed    = mRgba.data[mRgba.step[0] * i + mRgba.step[1] * j + 0] + red;
            int valueGreen  = mRgba.data[mRgba.step[0] * i + mRgba.step[1] * j + 1] + green;
            int valueBlue   = mRgba.data[mRgba.step[0] * i + mRgba.step[1] * j + 2] + blue;

            if(valueRed<256)
            {
                if(valueRed>0)
                    resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 0] = valueRed;
                else
                    resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 0] = 0;
            }
            else
                resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 0] = 255;

            if(valueGreen<256)
            {
                if(valueGreen>0)
                    resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 1] = valueGreen;
                else
                    resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 1] = 0;
            }
            else
                resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 1] = 255;

            if(valueBlue<256)
            {
                if(valueBlue>0)
                    resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 2] = valueBlue;
                else
                    resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 2] = 0;
            }
            else
                resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 2] = 255;
        }
    }

}

JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_MyImageProcessing_gammaCorrection
        (JNIEnv *, jclass, jlong addrRgba, jlong addrResultImage, jfloat gamma)
{
    Mat &mRgba = *(Mat *) addrRgba;
    Mat &resultImage = *(Mat *) addrResultImage;
    unsigned char lut[256];
    for (int i = 0; i < 256; i++)
    {

        lut[i] = saturate_cast<uchar>(pow((float)( i / 255.0),gamma ) * 255.0f);
        if(lut[i]<0)
            lut[i]=0;
        if(lut[i]>255)
            lut[i]=255;

    }
    for (int i = 0; i < mRgba.rows; i++) {
        for (int j = 0; j < mRgba.cols; j++) {
            resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 0] = lut[mRgba.data[mRgba.step[0] * i + mRgba.step[1] * j + 0]];
            resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 1] = lut[mRgba.data[mRgba.step[0] * i + mRgba.step[1] * j + 1]];
            resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 2] = lut[mRgba.data[mRgba.step[0] * i + mRgba.step[1] * j + 2]];
        }

    }

}


