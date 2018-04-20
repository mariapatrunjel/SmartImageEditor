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
    Mat &dst = *(Mat *) addrResultImage;

    Mat gray;
    cvtColor(src,gray,CV_RGB2GRAY);
    const int MEDIAN_BLUR_FILTER_SIZE = 7;
    medianBlur(gray,gray,MEDIAN_BLUR_FILTER_SIZE);

    Mat edges;
    const int LAPLACIAN_FILTER_SIZE = 5;
    Laplacian(gray,edges,CV_8U,LAPLACIAN_FILTER_SIZE);

    Mat mask = Mat(dst.size(),CV_8UC3);
    const int EDGES_THRESHOLD = 80;
    threshold(edges,mask,EDGES_THRESHOLD,255,THRESH_BINARY_INV);

     Size size = src.size();
    Size smallSize;
    smallSize.width = size.width/2;
    smallSize.height = size.height/2;

    Mat smallImg = Mat(smallSize,CV_8UC3);
    resize(src,smallImg,smallSize,0,0,INTER_LINEAR);

    Mat tmp = Mat(smallSize,CV_8UC3);
    int repetitions = 1;
    for(int i=0;i<repetitions;i++){
        int ksize = 9;
        double sigmaColor = 9;
        double sigmaSpace = 7;
        bilateralFilter(smallImg, tmp,ksize,sigmaColor,sigmaSpace);
        bilateralFilter(tmp, smallImg, ksize, sigmaColor,sigmaSpace);
    }
    Mat bigImg;
    resize(smallImg,bigImg,size,0,0,INTER_LINEAR);

    dst.setTo(0);
    mask.copyTo(dst,mask);


}

JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing_sketchFilter
        (JNIEnv *, jclass, jlong addrRgba, jlong addrResultImage)
{
    Mat &src = *(Mat *) addrRgba;
    Mat &mask = *(Mat *) addrResultImage;

    Mat gray;
    cvtColor(src, gray, CV_RGB2GRAY);
    const int MEDIAN_BLUR_FILTER_SIZE = 7;
    medianBlur(gray, gray, MEDIAN_BLUR_FILTER_SIZE);

    Mat edges;
    const int LAPLACIAN_FILTER_SIZE = 7;
    Laplacian(gray, edges, CV_8U, LAPLACIAN_FILTER_SIZE);

    //Mat mask = Mat(dst.size(),CV_8UC3);
    const int EDGES_THRESHOLD = 100;
    threshold(edges, mask, EDGES_THRESHOLD, 255, THRESH_BINARY_INV);
}

JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing_colorMapFilter
        (JNIEnv *, jclass,jlong addrRgba, jlong addrResultImage,jint colorMapName)
{
    Mat &rgba = *(Mat *) addrRgba;
    Mat &result = *(Mat *) addrResultImage;

    cvtColor(rgba, result, CV_RGB2GRAY);
    applyColorMap(result,result,colorMapName);
}

JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing_histogramEqualizationYCbCr
        (JNIEnv *, jclass,jlong addrRgba, jlong addrResultImage, jint channel)
{
    Mat &inputImage = *(Mat *) addrRgba;
    Mat &result = *(Mat *) addrResultImage;
    Mat ycrcb;

    cvtColor(inputImage,ycrcb,CV_RGB2YCrCb);
    vector<Mat> channels;
    split(ycrcb,channels);
    equalizeHist(channels[channel], channels[channel]);
    merge(channels,ycrcb);
    cvtColor(ycrcb,result,CV_YCrCb2RGB);
}

JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing_histogramEqualizationHSV
        (JNIEnv *, jclass,jlong addrRgba, jlong addrResultImage,jint channel)
{
    Mat &inputImage = *(Mat *) addrRgba;
    Mat &result = *(Mat *) addrResultImage;
    Mat hsv;

    cvtColor(inputImage,hsv,CV_RGB2HSV);
    vector<Mat> channels;
    split(hsv,channels);
    equalizeHist(channels[channel], channels[channel]);
    merge(channels,hsv);
    cvtColor(hsv,result,CV_HSV2RGB);
}

JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing_redTonedFillter
        (JNIEnv *, jclass, jlong addrRgba, jlong addrResultImage, jdouble alpha)
{
    Mat &mRgba = *(Mat *) addrRgba;
    Mat &resultImage = *(Mat *) addrResultImage;
    double u = ((alpha + 45) * M_PI) / 180;
    double ax = cos(u);
    double ay = sin(u);

    unsigned char lut[256];
    // Change with sin function
    for (int i = 0; i < 256; i++) {
        //double t = i/255.0f;
        lut[i] = 255.0f * sin(i * M_PI / 510.0f);
        if (lut[i] < 0)
            lut[i] = 0;
        if (lut[i] > 255)
            lut[i] = 255;

    }
    for (int i = 0; i < mRgba.rows; i++) {
        for (int j = 0; j < mRgba.cols; j++) {
            resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 0] = lut[mRgba.data[mRgba.step[0] * i + mRgba.step[1] * j + 0]];
            resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 1] = mRgba.data[mRgba.step[0] * i + mRgba.step[1] * j + 1];
            resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 2] = mRgba.data[mRgba.step[0] * i + mRgba.step[1] * j + 2];
        }

    }
}
JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing_greenTonedFillter
        (JNIEnv *, jclass, jlong addrRgba, jlong addrResultImage, jdouble alpha)
{
    Mat &mRgba = *(Mat *) addrRgba;
    Mat &resultImage = *(Mat *) addrResultImage;
    double u = ((alpha + 45) * M_PI) / 180;
    double ax = cos(u);
    double ay = sin(u);

    unsigned char lut[256];
// Change with sin function
    for (int i = 0; i < 256; i++) {
//double t = i/255.0f;
        lut[i] = 255.0f * sin(i * M_PI / 510.0f);
        if (lut[i] < 0)
            lut[i] = 0;
        if (lut[i] > 255)
            lut[i] = 255;

    }
    for (int i = 0; i < mRgba.rows; i++) {
        for (int j = 0; j < mRgba.cols; j++) {
            resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 0] = mRgba.data[mRgba.step[0] * i + mRgba.step[1] * j + 0];
            resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 1] = lut[mRgba.data[mRgba.step[0] * i + mRgba.step[1] * j + 1]];
            resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 2] = mRgba.data[mRgba.step[0] * i + mRgba.step[1] * j + 2];
        }

    }
}


JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing_blueTonedFillter
        (JNIEnv *, jclass, jlong addrRgba, jlong addrResultImage, jdouble alpha)
{
    Mat &mRgba = *(Mat *) addrRgba;
    Mat &resultImage = *(Mat *) addrResultImage;
    double u = ((alpha + 45) * M_PI) / 180;
    double ax = cos(u);
    double ay = sin(u);

    unsigned char lut[256];
    // Change with sin function
    for (int i = 0; i < 256; i++) {
        //double t = i/255.0f;
        lut[i] = 255.0f * sin(i * M_PI / 510.0f);
        if (lut[i] < 0)
            lut[i] = 0;
        if (lut[i] > 255)
            lut[i] = 255;

    }
    for (int i = 0; i < mRgba.rows; i++) {
        for (int j = 0; j < mRgba.cols; j++) {
            resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 0] = mRgba.data[mRgba.step[0] * i + mRgba.step[1] * j + 0];
            resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 1] = mRgba.data[mRgba.step[0] * i + mRgba.step[1] * j + 1];
            resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 2] = lut[mRgba.data[mRgba.step[0] * i + mRgba.step[1] * j + 2]];
        }

    }
}

JNIEXPORT void JNICALL Java_com_maria_patrunjel_smartimageeditor_utils_MyImageProcessing_interpolate
        (JNIEnv *, jclass, jlong addrRgba, jlong addrResultImage, jdouble alpha)
{
    Mat &mRgba = *(Mat *) addrRgba;
    Mat &resultImage = *(Mat *) addrResultImage;
    unsigned char lut[256];
    double u = ((alpha + 45)*M_PI)/180;
    double v = ((135-alpha)*M_PI)/180;
    double r = (255.0f*sqrt(2.0f))/cos(alpha*M_PI/180.0f);
    double ax = cos(u)*r, bx=cos(v)*r;
    double ay = sin(u)*r, by=sin(v)*r;

    for(double t=0;t<=1;t+=0.001)
        {
            double xt = (-2 * t * t * t + 3 * t * t) * 255.0f + (t*t*t-2*t*t+t)*ax+(t*t*t-t*t)*bx;
            double yt = (-2 * t * t * t + 3 * t * t) * 255.0f + (t*t*t-2*t*t+t)*ay+(t*t*t-t*t)*by;
            if(xt+0.5 <256 )
                lut[(int)(xt+0.5)]=(int)(yt+0.5);
        }
    for (int i = 0; i < mRgba.rows; i++) {
        for (int j = 0; j < mRgba.cols; j++) {
            resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 0] = lut[mRgba.data[mRgba.step[0] * i + mRgba.step[1] * j + 0]];
            resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 1] = mRgba.data[mRgba.step[0] * i + mRgba.step[1] * j + 1];
            resultImage.data[resultImage.step[0] * i + resultImage.step[1] * j + 2] = mRgba.data[mRgba.step[0] * i + mRgba.step[1] * j + 2];
        }

    }
}