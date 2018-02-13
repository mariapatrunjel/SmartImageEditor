package com.maria.patrunjel.smartimageeditor;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends Activity {

    public static final int IMAGE_GALLERY_REQUEST = 20;
    private ImageView imgPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onCameraActivityClicked(View view){
        Intent intent = new Intent(this, CameraActivity.class);
        startActivityForResult(intent,0);

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
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap image = BitmapFactory.decodeStream(inputStream);
                    imgPicture =(ImageView)findViewById(R.id.imageView);
                    imgPicture.setImageBitmap(image);

                }catch(FileNotFoundException e){
                    e.printStackTrace();
                    Toast.makeText(this,"Unable to open image",Toast.LENGTH_LONG).show();
                }

            }
        }
    }

}


















