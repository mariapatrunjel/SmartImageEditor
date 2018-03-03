package com.maria.patrunjel.smartimageeditor.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;

import com.maria.patrunjel.smartimageeditor.retained.fragments.CameraSettingsRetainedFragment;
import com.maria.patrunjel.smartimageeditor.utils.MyImageProcessing;
import com.maria.patrunjel.smartimageeditor.utils.MyJavaCameraView;
import com.maria.patrunjel.smartimageeditor.R;
import com.maria.patrunjel.smartimageeditor.utils.Photo;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class CameraActivity extends Activity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static final int FLASH_MODE_OFF = 0;
    private static final int FLASH_MODE_ON = 1;
    private static final int FLASH_MODE_AUTO = 2;

    private MyJavaCameraView javaCameraView;
    private Mat mRgba;
    private String currentFilter = "Normal";
    private Integer redValue=0, greenValue = 0,blueValue = 0;
    private Float brightness = 1.0f;
    private int cameraId = 0;
    private int flashMode = FLASH_MODE_OFF;



    private static final String TAG_RETAINED_FRAGMENT = "CameraSettingsRetainedFragment";
    private CameraSettingsRetainedFragment mRetainedFragment;

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
        mRetainedFragment = (CameraSettingsRetainedFragment) fm.findFragmentByTag(TAG_RETAINED_FRAGMENT);

        // create the fragment and data the first time
        if (mRetainedFragment == null) {
            // add the fragment
            mRetainedFragment = new CameraSettingsRetainedFragment();
            fm.beginTransaction().add(mRetainedFragment, TAG_RETAINED_FRAGMENT).commit();
            // load data from a data source or perform any calculation
            mRetainedFragment.setFilter(currentFilter);
            mRetainedFragment.setRedValue(redValue);
            mRetainedFragment.setGreenValue(greenValue);
            mRetainedFragment.setBlueValue(blueValue);
            mRetainedFragment.setBrightness(brightness);
            mRetainedFragment.setCameraId(cameraId);
            mRetainedFragment.setFlashMode(flashMode);
        }

        currentFilter = mRetainedFragment.getFilter();
        redValue = mRetainedFragment.getRedValue();
        greenValue = mRetainedFragment.getGreenValue();
        blueValue = mRetainedFragment.getBlueValue();
        brightness = mRetainedFragment.getBrightness();
        cameraId = mRetainedFragment.getCameraId();
        flashMode = mRetainedFragment.getFlashMode();

        javaCameraView = (MyJavaCameraView) findViewById(R.id.java_camera_view);
        javaCameraView.setVisibility(SurfaceView.VISIBLE);
        javaCameraView.setCvCameraViewListener(this);
        javaCameraView.setCameraIndex(cameraId);

        //setFlashModeImage();
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
        setFlashModeImage();
    }

    @Override
    public void onCameraViewStopped() {
        mRgba.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        mRgba = inputFrame.rgba();
        return MyImageProcessing.processImage(mRgba,currentFilter,redValue,greenValue,blueValue,brightness);
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
        javaCameraView.takePicture(this,currentFilter,redValue,greenValue,blueValue,brightness,cameraId);
    }

    public void onClickSettingsActivity(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("red",redValue);
        intent.putExtra("green",greenValue);
        intent.putExtra("blue",blueValue);
        intent.putExtra("brightness",brightness);
        startActivityForResult(intent,0);

    }

    public void onNormalFilterClicked(View view) {
        currentFilter = "Normal";
        mRetainedFragment.setFilter(currentFilter);
    }

    public void onSepiaFilterClicked(View view) {
        currentFilter = "Sepia";
        mRetainedFragment.setFilter(currentFilter);
    }

    public void onRedFilterClicked(View view) {
        currentFilter = "Red";
        mRetainedFragment.setFilter(currentFilter);
    }

    public void onBlueFilterClicked(View view) {
        currentFilter = "Blue";
        mRetainedFragment.setFilter(currentFilter);
    }

    public void onGreenFilterClicked(View view) {
        currentFilter = "Green";
        mRetainedFragment.setFilter(currentFilter);
    }

    public void onMagentaFilterClicked(View view) {
        currentFilter = "Magenta";
        mRetainedFragment.setFilter(currentFilter);
    }

    public void onSwapCamera(View view) {
        cameraId = cameraId^1;
        mRetainedFragment.setCameraId(cameraId);
        javaCameraView.disableView();
        javaCameraView.setCameraIndex(cameraId);
        javaCameraView.enableView();
    }

    public void onFlashlight(View view) {
        flashMode  = (flashMode + 1)%3; ;
        mRetainedFragment.setFlashMode(flashMode);
        setFlashModeImage();
    }

    private void setFlashModeImage(){
        flashMode = mRetainedFragment.getFlashMode();
        ImageButton imageButton = (ImageButton)findViewById(R.id.flashlight);
        if(flashMode == FLASH_MODE_OFF) {
            javaCameraView.turnTheFlashOff();
            imageButton.setImageResource(R.drawable.flash_light_off);
        }
        if(flashMode == FLASH_MODE_AUTO) {
            javaCameraView.turnTheFlashAuto();
            imageButton.setImageResource(R.drawable.flash_light_auto);
        }
        if(flashMode == FLASH_MODE_ON) {
            javaCameraView.turnTheFlashOn();
            imageButton.setImageResource(R.drawable.flash_light_on);
        }
    }

}

