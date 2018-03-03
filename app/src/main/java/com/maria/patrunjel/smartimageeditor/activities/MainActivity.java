package com.maria.patrunjel.smartimageeditor.activities;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.maria.patrunjel.smartimageeditor.retained.fragments.ImageRetainedFragment;
import com.maria.patrunjel.smartimageeditor.utils.MyImageProcessing;
import com.maria.patrunjel.smartimageeditor.utils.Photo;
import com.maria.patrunjel.smartimageeditor.R;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class MainActivity extends Activity {

    private static final int IMAGE_GALLERY_REQUEST = 20;
    private Bitmap currentImage;

    private String currentFilter = "Normal";
    private Integer redValue=0, greenValue = 0,blueValue = 0;
    private Float brightness = 1.0f;

    private static final String TAG_RETAINED_FRAGMENT = "ImageRetainedFragment";
    private ImageRetainedFragment mRetainedFragment;

    static {
        System.loadLibrary("MyOpencvLibs");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find the retained fragment on activity restarts
        FragmentManager fm = getFragmentManager();
        mRetainedFragment = (ImageRetainedFragment) fm.findFragmentByTag(TAG_RETAINED_FRAGMENT);

        // create the fragment and data the first time
        if (mRetainedFragment == null) {
            // add the fragment
            mRetainedFragment = new ImageRetainedFragment();
            fm.beginTransaction().add(mRetainedFragment, TAG_RETAINED_FRAGMENT).commit();
            // load data from a data source or perform any calculation
           setModifiers();
        }
        getModifiers();
        changePicture();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);//must store the new intent unless getIntent() will return the old one
        processIntentData();
    }

    private void processIntentData(){
        Intent intent = getIntent();
        if(intent!=null){
            Photo photo = Photo.getInstance();
            currentImage = photo.getImage();
            mRetainedFragment.setImage(currentImage);
        }
        currentImage = mRetainedFragment.getImage();
        changePicture();
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            if(requestCode == IMAGE_GALLERY_REQUEST){
                Uri imageUri = data.getData();
                InputStream inputStream;
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);
                    currentImage = BitmapFactory.decodeStream(inputStream);
                    mRetainedFragment.setImage(currentImage);
                    resetModifiers();
                    changePicture();

                }catch(FileNotFoundException e){
                    e.printStackTrace();
                    Toast.makeText(this,"Unable to open image",Toast.LENGTH_LONG).show();
                }

            }
        }
    }

    public void onImageGalleryButtonClicked(View view){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        Uri data = Uri.parse(pictureDirectoryPath);
        photoPickerIntent.setDataAndType(data,"image/*");
        startActivityForResult(photoPickerIntent,IMAGE_GALLERY_REQUEST);
    }

    public void onCameraActivityButtonClicked(View view){
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);

    }

    public void onNormalFilterClicked(View view) {
        currentFilter = "Normal";
        mRetainedFragment.setFilter(currentFilter);
        changePicture();
    }

    public void onSepiaFilterClicked(View view) {
        currentFilter = "Sepia";
        mRetainedFragment.setFilter(currentFilter);
        changePicture();
    }

    public void onRedFilterClicked(View view) {
        currentFilter = "Red";
        mRetainedFragment.setFilter(currentFilter);
        changePicture();
    }

    public void onBlueFilterClicked(View view) {
        currentFilter = "Blue";
        mRetainedFragment.setFilter(currentFilter);
        changePicture();
    }

    public void onGreenFilterClicked(View view) {
        currentFilter = "Green";
        mRetainedFragment.setFilter(currentFilter);
        changePicture();
    }

    public void onMagentaFilterClicked(View view) {
        currentFilter = "Magenta";
        mRetainedFragment.setFilter(currentFilter);
        changePicture();
    }

    private Bitmap getModifiedImage()
    {
        if(currentImage != null) {
            // convert Bitmap to Mat
            Mat mRgba = new Mat();
            Bitmap bmp32 = currentImage.copy(Bitmap.Config.ARGB_8888, true);
            Utils.bitmapToMat(bmp32, mRgba);

            //process image
            MyImageProcessing.processImage(mRgba, currentFilter, blueValue, greenValue, redValue, brightness);

            // convert Mat to Bitmap
            Bitmap modifiedImage = (Bitmap.createBitmap(mRgba.cols(), mRgba.rows(), Bitmap.Config.ARGB_8888));
            Utils.matToBitmap(mRgba, modifiedImage);

            return modifiedImage;
        }
        return null;
    }

    private void changePicture(){
        Bitmap modifiedImage = getModifiedImage();
        if(modifiedImage != null) {
            ImageView imgPicture = (ImageView) findViewById(R.id.imageView);
            imgPicture.setImageBitmap(modifiedImage);
        }
    }

    private void resetModifiers(){
        currentFilter = "Normal";
        redValue=0;
        greenValue = 0;
        blueValue = 0;
        brightness = 1.0f;

        mRetainedFragment.setFilter(currentFilter);
        mRetainedFragment.setRedValue(redValue);
        mRetainedFragment.setGreenValue(greenValue);
        mRetainedFragment.setBlueValue(blueValue);
        mRetainedFragment.setBrightness(brightness);
    }

    private void setModifiers(){
        mRetainedFragment.setImage(currentImage);
        mRetainedFragment.setFilter(currentFilter);
        mRetainedFragment.setRedValue(redValue);
        mRetainedFragment.setGreenValue(greenValue);
        mRetainedFragment.setBlueValue(blueValue);
        mRetainedFragment.setBrightness(brightness);
    }

    private void getModifiers(){
        currentImage = mRetainedFragment.getImage();
        currentFilter = mRetainedFragment.getFilter();
        redValue = mRetainedFragment.getRedValue();
        greenValue = mRetainedFragment.getGreenValue();
        blueValue = mRetainedFragment.getBlueValue();
        brightness = mRetainedFragment.getBrightness();
    }
}


















