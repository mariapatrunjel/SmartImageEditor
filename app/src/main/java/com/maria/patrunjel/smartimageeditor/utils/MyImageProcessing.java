package com.maria.patrunjel.smartimageeditor.utils;

import org.opencv.core.Mat;

public class MyImageProcessing {

     static {
          System.loadLibrary("MyOpencvLibs");
     }

     private static native void changeRGBChannels(long addrRgba, long addrResultImage, int red,int green,int blue);
     private static native void gammaCorrection(long addrRgba, long addrResultImage, float gamma);
     private static native void sepiaFilter(long addrRgba, long addrResultImage);


     public static Mat processImage(Mat image, String currentFilter ,int redValue,int greenValue,int blueValue,float brightness){

          changeRGBChannels(image.getNativeObjAddr(),image.getNativeObjAddr(),redValue,greenValue,blueValue);
          gammaCorrection(image.getNativeObjAddr(),image.getNativeObjAddr(),brightness);
          switch (currentFilter) {
               case "Sepia":
                    sepiaFilter(image.getNativeObjAddr(),image.getNativeObjAddr());
                    break;
               case "Red":
                    changeRGBChannels(image.getNativeObjAddr(),image.getNativeObjAddr(),80,0,0);
                    break;
               case "Green":
                    changeRGBChannels(image.getNativeObjAddr(),image.getNativeObjAddr(),0,80,0);
                    break;
               case "Blue":
                    changeRGBChannels(image.getNativeObjAddr(),image.getNativeObjAddr(),0,0,80);
                    break;
               case "Magenta":
                    changeRGBChannels(image.getNativeObjAddr(),image.getNativeObjAddr(),40,0,40);
                    break;
               default:
                    return image;

          }
          return image;
     }
}
