#include "com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing.h"


JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing_changeRGBChannels
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

JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing_gammaCorrection
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

JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing_sepiaFilter
        (JNIEnv *, jclass, jlong addrRgba, jlong addrResultImage)
{
    Mat &mRgba = *(Mat *) addrRgba;
    Mat &resultImage = *(Mat *) addrResultImage;
    int r,b,g,tr,tg,tb;
    for (int i = 0; i < mRgba.rows; i++) {
        for (int j = 0; j < mRgba.cols; j++) {
            r = mRgba.data[resultImage.step[0] * i + resultImage.step[1] * j + 0];
            g = mRgba.data[resultImage.step[0] * i + resultImage.step[1] * j + 1];
            b = mRgba.data[resultImage.step[0] * i + resultImage.step[1] * j + 2];

            tr = (int)(0.393*r + 0.769*g + 0.189*b);
            tg = (int)(0.349*r + 0.686*g + 0.168*b);
            tb = (int)(0.272*r + 0.534*g + 0.131*b);

            // Red
            if (tr > 255)
                resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 0] = 255;
              else
                resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 0] = tr;

            // Green
            if (tg > 255)
                resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 1] = 255;
            else
                resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 1] = tg;

            // Blue
            if (tb > 255)
                resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 2] = 255;
            else
                resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 2] = tb;

        }

    }
}

JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing_cartoonFilter
        (JNIEnv *, jclass, jlong addrRgba, jlong addrResultImage)
{
    Mat &src = *(Mat *) addrRgba;
    Mat &mask = *(Mat *) addrResultImage;

    Mat gray;
    cvtColor(src,gray,CV_BGR2GRAY);
    const int MEDIAN_BLUR_FILTER_SIZE = 7;
    medianBlur(gray,gray,MEDIAN_BLUR_FILTER_SIZE);

    Mat edges;
    const int LAPLACIAN_FILTER_SIZE = 5;
    Laplacian(gray,edges,CV_8U,LAPLACIAN_FILTER_SIZE);

    //Mat mask = Mat(dst.size(),CV_8UC3);
    const int EDGES_THRESHOLD = 80;
    threshold(edges,mask,EDGES_THRESHOLD,255,THRESH_BINARY_INV);

    /*
    Size size = src.size();
    Size smallSize;
    smallSize.width = size.width/2;
    smallSize.height = size.height/2;

    Mat smallImg = Mat(smallSize,CV_8UC3);
    resize(src,smallImg,smallSize,0,0,INTER_LINEAR);

    Mat tmp = Mat(smallSize,CV_8UC3);
    int repetitions = 7;
    for(int i=0;i<repetitions;i++){
        int ksize = 9;
        double sigmaColor = 9;
        double sigmaSpace = 7;
        bilateralFilter(smallImg, tmp,ksize,sigmaColor,sigmaSpace);
        bilateralFilter(tmp, smallImg, ksize, sigmaColor,sigmaSpace);
    }
    Mat bigImg;
    resize(smallImg,bigImg,size,0,0,INTER_LINEAR);

    */
    //dst.setTo(0);
    //mask.copyTo(dst,mask);


    /*
    edgePreservingFilter(src, dst, 1, 60, 0.4f);
    detailEnhance(src, dst, 10, 0.15f);
    pencilSketch(src, dst_gray, dst_color, 60, 0.07f, 0.02f);
    stylization(src, dst, 60, 0.45f);
     */
}

JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing_sketchFilter
        (JNIEnv *, jclass, jlong addrRgba, jlong addrResultImage)
{
    Mat &src = *(Mat *) addrRgba;
    Mat &mask = *(Mat *) addrResultImage;

    Mat gray;
    cvtColor(src, gray, CV_BGR2GRAY);
    const int MEDIAN_BLUR_FILTER_SIZE = 7;
    medianBlur(gray, gray, MEDIAN_BLUR_FILTER_SIZE);

    Mat edges;
    const int LAPLACIAN_FILTER_SIZE = 7;
    Laplacian(gray, edges, CV_8U, LAPLACIAN_FILTER_SIZE);

    //Mat mask = Mat(dst.size(),CV_8UC3);
    const int EDGES_THRESHOLD = 100;
    threshold(edges, mask, EDGES_THRESHOLD, 255, THRESH_BINARY_INV);
}