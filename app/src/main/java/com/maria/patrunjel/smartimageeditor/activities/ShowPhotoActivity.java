package com.maria.patrunjel.smartimageeditor.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.maria.patrunjel.smartimageeditor.retained.fragments.ImageRetainedFragment;
import com.maria.patrunjel.smartimageeditor.utils.Photo;
import com.maria.patrunjel.smartimageeditor.R;
import com.maria.patrunjel.smartimageeditor.utils.Utils;

public class ShowPhotoActivity extends Activity {
    private Bitmap currentImage;
    private static final String TAG_RETAINED_FRAGMENT = "ImageRetainedFragment";
    private ImageRetainedFragment mRetainedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);

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

        Intent intent = getIntent();
        if(intent!=null){
            Photo photo = Photo.getInstance();
            currentImage = photo.getImage();
            mRetainedFragment.setImage(currentImage);
        }

        ImageView imgPicture =(ImageView)findViewById(R.id.imageView);
        imgPicture.setImageBitmap(currentImage);
    }

    public void onEditClicked(View view){
        Photo photo = Photo.getInstance();
        photo.setImage(currentImage);

        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }

    public void onSaveClicked(View view){
       Utils.saveImage(this,currentImage);
        finish();
    }
}
