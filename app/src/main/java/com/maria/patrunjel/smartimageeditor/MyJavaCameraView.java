package com.maria.patrunjel.smartimageeditor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.media.MediaActionSound;
import android.util.AttributeSet;
import org.opencv.android.*;
import org.opencv.core.Mat;


public class MyJavaCameraView extends JavaCameraView{

    public MyJavaCameraView(Context context, int cameraId) {
        super(context, cameraId);
    }

    public MyJavaCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void turnOffTheFlash() {
        Camera.Parameters params = mCamera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        mCamera.setParameters(params);
    }

    public void turnOnTheFlash() {
        Camera.Parameters params = mCamera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
        mCamera.setParameters(params);
    }

    public void takePicture(final Context context, final Mat picture, final Integer cameraId) {
        Camera.PictureCallback callback = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                MediaActionSound sound = new MediaActionSound();
                sound.play(MediaActionSound.SHUTTER_CLICK);

                //SaveImage.saveImageToExternalStorage(context,picture,getResources().getConfiguration().orientation,cameraId);
                Bitmap finalBitmap = Utils.matToFinalBitmap(picture, getResources().getConfiguration().orientation, cameraId);
                Photo photo = Photo.getInstance();
                photo.setImage(finalBitmap);

                Intent intent = new Intent(context, ShowPhotoActivity.class);
                context.startActivity(intent);
            }
        };

        mCamera.takePicture(null, null, callback);
    }
}
