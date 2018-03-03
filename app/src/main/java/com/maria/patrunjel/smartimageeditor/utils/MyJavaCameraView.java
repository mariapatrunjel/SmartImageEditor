package com.maria.patrunjel.smartimageeditor.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.MediaActionSound;
import android.util.AttributeSet;

import com.maria.patrunjel.smartimageeditor.activities.ShowPhotoActivity;

import org.opencv.android.*;
import org.opencv.core.CvType;
import org.opencv.core.Mat;


public class MyJavaCameraView extends JavaCameraView{

    public MyJavaCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void turnTheFlashOff() {
        Camera.Parameters params = mCamera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        mCamera.setParameters(params);
    }

    public void turnTheFlashOn() {
        Camera.Parameters params = mCamera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
        mCamera.setParameters(params);
    }

    public void turnTheFlashAuto() {
        Camera.Parameters params = mCamera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
        mCamera.setParameters(params);
    }

    public void takePicture(final Context context, final String currentFilter,final int blueValue,final int greenValue,final int redValue,final float brightness,final Integer cameraId) {
        Camera.PictureCallback callback = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {

                MediaActionSound sound = new MediaActionSound();
                sound.play(MediaActionSound.SHUTTER_CLICK);

                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

                // convert Bitmap to Mat
                Mat image = new Mat();
                Bitmap bmp32 = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                org.opencv.android.Utils.bitmapToMat(bmp32, image);

                //process image
                MyImageProcessing.processImage(image, currentFilter, blueValue, greenValue, redValue, brightness);

                Bitmap finalBitmap = Utils.matToFinalBitmap(image, getResources().getConfiguration().orientation, cameraId);

                //Bitmap finalBitmap = Utils.byteToFinalBitmap(data, getResources().getConfiguration().orientation, cameraId);

                Photo photo = Photo.getInstance();
                photo.setImage(finalBitmap);

                Intent intent = new Intent(context, ShowPhotoActivity.class);
                context.startActivity(intent);
            }
        };

        mCamera.takePicture(null, null, callback);
    }
}
