package com.maria.patrunjel.smartimageeditor;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends Activity {

    private static final int IMAGE_GALLERY_REQUEST = 20;
    private Bitmap currentImage;
    private static final String TAG_RETAINED_FRAGMENT = "ImageRetainedFragment";
    private ImageRetainedFragment mRetainedFragment;

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
            mRetainedFragment.setImage(currentImage);
        }

        currentImage = mRetainedFragment.getImage();
        ImageView imgPicture =(ImageView)findViewById(R.id.imageView);
        imgPicture.setImageBitmap(currentImage);
    }

    public void onCameraActivityClicked(View view){
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);

    }

    public void onImageGalleryClicked(View view){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        Uri data = Uri.parse(pictureDirectoryPath);
        photoPickerIntent.setDataAndType(data,"image/*");
        startActivityForResult(photoPickerIntent,IMAGE_GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            if(requestCode == IMAGE_GALLERY_REQUEST){
                Uri imageUri = data.getData();
                InputStream inputStream;
                ImageView imgPicture;
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);
                    currentImage = BitmapFactory.decodeStream(inputStream);
                    mRetainedFragment.setImage(currentImage);
                    imgPicture =(ImageView)findViewById(R.id.imageView);
                    imgPicture.setImageBitmap(currentImage);

                }catch(FileNotFoundException e){
                    e.printStackTrace();
                    Toast.makeText(this,"Unable to open image",Toast.LENGTH_LONG).show();
                }

            }
        }
    }

}


















