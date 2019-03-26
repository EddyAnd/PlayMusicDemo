package com.example.playmusicdemo.activitys;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.playmusicdemo.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ResultActivity extends AppCompatActivity {
    private ImageView mImageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);
        mImageView = findViewById(R.id.show_image);
        String mPath = getIntent().getStringExtra("path");
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(mPath);
            Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
            Matrix matrix = new Matrix();
            matrix.setRotate(90);
            bitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
            mImageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                if(fileInputStream != null){
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
