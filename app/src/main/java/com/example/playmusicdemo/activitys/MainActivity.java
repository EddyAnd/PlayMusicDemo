package com.example.playmusicdemo.activitys;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.playmusicdemo.R;
import com.example.playmusicdemo.utils.AndroidUtil;
import com.example.playmusicdemo.views.InputView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    private  static final String TAG = "MainActivity";
    private InputView mInputView;
    private ImageView mOneImageView;
    private ImageView mTwoImageView;
    private ImageView mThreeImageView;
    private String mPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInputView = findViewById(R.id.password);
        mOneImageView = findViewById(R.id.show_one_image);
        mTwoImageView = findViewById(R.id.show_two_image);
        mThreeImageView = findViewById(R.id.show_three_image);
        mPath = Environment.getExternalStorageDirectory().getPath()+"/"+"temp.png";
        writeRecordStorageTask();
        startCameraTask();
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
    private String generateFileName() {
        return UUID.randomUUID().toString()+".png";
    }
    private String mPhotoPath;
    public void OpenCameraTwo(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(mPath);
        Uri photoUri = FileProvider.getUriForFile(
                getApplicationContext(),
                getPackageName() + ".fileprovider",
                file);
        Log.e("=====photoUri==",photoUri.getPath());
        intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
        startActivityForResult(intent,2);

    }
    private static final int RECORD_WRITE_EXTERNAL_STORAGE = 128;
    private static final int CAMERA = 129;
    private boolean hasPermission(String[] perms) {
        return EasyPermissions.hasPermissions(getApplicationContext(), perms);
    }

    @AfterPermissionGranted(RECORD_WRITE_EXTERNAL_STORAGE)
    public void writeRecordStorageTask() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (hasPermission(perms)) {

        } else {
            EasyPermissions.requestPermissions(
                    this,
                    "获取存储权限",
                    RECORD_WRITE_EXTERNAL_STORAGE,
                    perms);
        }
    }
    @AfterPermissionGranted(CAMERA)
    public void startCameraTask() {
        String[] perms = {Manifest.permission.CAMERA};
        if (hasPermission(perms)) {

        } else {
            EasyPermissions.requestPermissions(
                    this,
                    "获取相机权限",
                    CAMERA,
                    perms);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && data != null){
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
                    if(fileInputStream != null){
                        fileInputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//        else if (requestCode == 3 && data != null){
//            String mPath = data.getStringExtra("path");
//            FileInputStream fileInputStream = null;
//            try {
//                fileInputStream = new FileInputStream(mPath);
//                Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
//                Matrix matrix = new Matrix();
//                matrix.setRotate(90);
//                bitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
//                mThreeImageView.setImageBitmap(bitmap);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }finally {
//                try {
//                    if(fileInputStream != null){
//                        fileInputStream.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

    public void OpenCameraThree(View view) {
        startActivity(new Intent(this,CustomCamera.class));
    }
}
