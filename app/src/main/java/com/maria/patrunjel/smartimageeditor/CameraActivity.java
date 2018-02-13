package com.maria.patrunjel.smartimageeditor;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.media.MediaActionSound;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class CameraActivity extends Activity implements CameraBridgeViewBase.CvCameraViewListener2 {
    private JavaCameraView javaCameraView;
    private Mat mRgba;
    private String currentFilter = "Normal";
    private Integer redValue=0, greenValue = 0,blueValue = 0;
    private Float brightness = 1.0f;
    private int cameraId = 0;
    private Boolean flashlightOn = false;

    private static final String TAG_RETAINED_FRAGMENT = "RetainedFragment";
    private RetainedFragment mRetainedFragment;

    BaseLoaderCallback mLoaderCallBack = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case BaseLoaderCallback.SUCCESS: {
                    javaCameraView.enableView();
                    break;
                }
                default: {
                    super.onManagerConnected(status);
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // find the retained fragment on activity restarts
        FragmentManager fm = getFragmentManager();
        mRetainedFragment = (RetainedFragment) fm.findFragmentByTag(TAG_RETAINED_FRAGMENT);

        // create the fragment and data the first time
        if (mRetainedFragment == null) {
            // add the fragment
            mRetainedFragment = new RetainedFragment();
            fm.beginTransaction().add(mRetainedFragment, TAG_RETAINED_FRAGMENT).commit();
            // load data from a data source or perform any calculation
            mRetainedFragment.setFilter(currentFilter);
            mRetainedFragment.setRedValue(redValue);
            mRetainedFragment.setGreenValue(greenValue);
            mRetainedFragment.setBlueValue(blueValue);
            mRetainedFragment.setBrightness(brightness);
            mRetainedFragment.setCameraId(cameraId);
            mRetainedFragment.setFlashlightOn(flashlightOn);
        }

        currentFilter = mRetainedFragment.getFilter();
        redValue = mRetainedFragment.getRedValue();
        greenValue = mRetainedFragment.getGreenValue();
        blueValue = mRetainedFragment.getBlueValue();
        brightness = mRetainedFragment.getBrightness();
        cameraId = mRetainedFragment.getCameraId();
        flashlightOn = mRetainedFragment.getFlashlightOn();

        javaCameraView = (JavaCameraView) findViewById(R.id.java_camera_view);
        javaCameraView.setVisibility(SurfaceView.VISIBLE);
        javaCameraView.setCvCameraViewListener(this);
        javaCameraView.setCameraIndex(cameraId);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (OpenCVLoader.initDebug()) {
            mLoaderCallBack.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        } else {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_13, this, mLoaderCallBack);
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat(height, width, CvType.CV_8UC4);

    }

    @Override
    public void onCameraViewStopped() {
        mRgba.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        mRgba = inputFrame.rgba();
        return processImage(mRgba);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        redValue = intent.getIntExtra("red",0);
        greenValue = intent.getIntExtra("green",0);
        blueValue = intent.getIntExtra("blue",0);
        brightness =  intent.getFloatExtra("brightness",1.0f);

        mRetainedFragment.setRedValue(redValue);
        mRetainedFragment.setGreenValue(greenValue);
        mRetainedFragment.setBlueValue(blueValue);
        mRetainedFragment.setBrightness(brightness);

    }

    public void onTakePicture(View view) {
        MediaActionSound sound = new MediaActionSound();
        sound.play(MediaActionSound.SHUTTER_CLICK);
        SaveImage.saveImageToExternalStorage(this,processImage(mRgba),getResources().getConfiguration().orientation,cameraId);
    }

    public void onClickGrayFilter(View view) {
        currentFilter = "Gray";
        mRetainedFragment.setFilter(currentFilter);
    }

    public void onClickDarkFilter(View view) {
        currentFilter = "Dark";
        mRetainedFragment.setFilter(currentFilter);
    }

    public void onClickBrightFilter(View view) {
        currentFilter = "Bright";
        mRetainedFragment.setFilter(currentFilter);
    }

    public void onClickNormal(View view) {
        currentFilter = "Normal";
        mRetainedFragment.setFilter(currentFilter);
    }

    public void onClickBinarizationFilter(View view) {
        currentFilter = "Binarization";
        mRetainedFragment.setFilter(currentFilter);
    }

    public void onClickRedFilter(View view) {
        currentFilter = "Red";
        mRetainedFragment.setFilter(currentFilter);
    }

    public void onClickGreenFilter(View view) {
        currentFilter = "Green";
        mRetainedFragment.setFilter(currentFilter);
    }

    public void onClickBlueFilter(View view) {
        currentFilter = "Blue";
        mRetainedFragment.setFilter(currentFilter);
    }

    public void onClickSettingsActivity(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("red",redValue);
        intent.putExtra("green",greenValue);
        intent.putExtra("blue",blueValue);
        intent.putExtra("brightness",brightness);
        startActivityForResult(intent,0);

    }

    public Mat processImage(Mat image){

        MyImageProcessing.changeRGBChannels(image.getNativeObjAddr(),image.getNativeObjAddr(),redValue,greenValue,blueValue);
        MyImageProcessing.gammaCorrection(image.getNativeObjAddr(),image.getNativeObjAddr(),brightness);
        switch (currentFilter) {
            case "Gray":

                break;
            case "Bright":
                MyImageProcessing.brightFilter(image.getNativeObjAddr(),image.getNativeObjAddr());
                break;
            case "Dark":
                MyImageProcessing.darkFilter(image.getNativeObjAddr(),image.getNativeObjAddr());
                break;
            case "Binarization":
                MyImageProcessing.changeRGBChannels(image.getNativeObjAddr(),image.getNativeObjAddr(),40,0,40);
                break;
            case "Red":
                MyImageProcessing.changeRGBChannels(image.getNativeObjAddr(),image.getNativeObjAddr(),80,0,0);
                break;
            case "Green":
                MyImageProcessing.changeRGBChannels(image.getNativeObjAddr(),image.getNativeObjAddr(),0,80,0);
                break;
            case "Blue":
                MyImageProcessing.changeRGBChannels(image.getNativeObjAddr(),image.getNativeObjAddr(),0,0,80);
                break;
            default:
                return image;

        }
        return image;
    }

    public void onSwapCamera(View view) {
        cameraId = cameraId^1;
        mRetainedFragment.setCameraId(cameraId);
        javaCameraView.disableView();
        javaCameraView.setCameraIndex(cameraId);
        javaCameraView.enableView();
    }

    public void onFlashlight(View view) {
        flashlightOn = !flashlightOn;
        mRetainedFragment.setFlashlightOn(flashlightOn);
        if(flashlightOn)
            javaCameraView.turnOnTheFlash();
        else
            javaCameraView.turnOffTheFlash();
    }

}

