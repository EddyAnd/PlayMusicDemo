package com.example.playmusicdemo.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.playmusicdemo.R;
import com.example.playmusicdemo.views.InputView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private  static final String TAG = "MainActivity";
    private InputView mInputView;
    private ImageView mOneImageView;
    private ImageView mTwoImageView;
    private String mPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInputView = findViewById(R.id.password);
        mOneImageView = findViewById(R.id.show_one_image);
        mTwoImageView = findViewById(R.id.show_two_image);
        mPath = Environment.getExternalStorageDirectory().getPath()+"/"+"temp.png";

    }

    public void toRegister(View view) {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
       // overridePendingTransition(R.anim.open_enter, R.anim.open_exit);

    }

    public void OpenCameraOne(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,1);
    }

    public void OpenCameraTwo(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(mPath);
        Uri uri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        startActivityForResult(intent,2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            mOneImageView.setImageBitmap(bitmap);

        }else if(requestCode == 2){
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(mPath);
                Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
                mTwoImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }finally {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
