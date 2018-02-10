package com.maria.patrunjel.smartimageeditor;

class MyImageProcessing {

     static {
          System.loadLibrary("MyOpencvLibs");
     }
     public static native void darkFilter(long addrRgba, long addrResultImage);
     public static native void brightFilter(long addrRgba, long addrResultImage);
     public static native void redFilter(long addrRgba, long addrResultImage);
     public static native void greenFilter(long addrRgba, long addrResultImage);
     public static native void blueFilter(long addrRgba, long addrResultImage);
     public static native void binarizationFilter(long addrRgba, long addrResultImage);
     public static native void changeRGBChannels(long addrRgba, long addrResultImage, int red,int green,int blue);
     public static native void gammaCorrection(long addrRgba, long addrResultImage, float gamma);


}
