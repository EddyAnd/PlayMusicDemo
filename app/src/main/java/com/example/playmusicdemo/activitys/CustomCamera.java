package com.example.playmusicdemo.activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.playmusicdemo.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CustomCamera extends Activity implements SurfaceHolder.Callback {
    private Camera mCamera;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder ;

    private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
         String    mPath = Environment.getExternalStorageDirectory().getPath()+"/"+"temp520.png";
            File file = new File(mPath);
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(data);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Intent intent = new Intent();
            intent.putExtra("path", mPath); //将计算的值回传回去
            setResult(2, intent);//resultCode只要大于1即可
            finish();
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_layout);
        mSurfaceView = findViewById(R.id.camera_surface);
        mSurfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.autoFocus(null);
            }
        });
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCamera();
        if(mSurfaceHolder != null){
            startCameraPreview(mCamera,mSurfaceHolder);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        realseCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public Camera getCamera(){
        if(mCamera == null){
            mCamera =Camera.open();
        }
        return mCamera;
    }

    /**
     * 开始预览相机内容
     */
    public void startCameraPreview(Camera camera,SurfaceHolder holder){
        if(camera != null){
            try {
                camera.setPreviewDisplay(holder);
                camera.setDisplayOrientation(90);
                camera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 释放相机资源
     */
    public void realseCamera(){
        if(mCamera != null){
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
          startCameraPreview(mCamera,mSurfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
         mCamera.stopPreview();
         startCameraPreview(mCamera,mSurfaceHolder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
         realseCamera();
    }

    public void startCapture(View view) {
        if (mCamera != null) {
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setPictureFormat(ImageFormat.JPEG);
            parameters.setPreviewSize(800, 400);
            parameters.setFocusMode(Camera.Parameters.FLASH_MODE_AUTO);
            mCamera.autoFocus(new Camera.AutoFocusCallback() {

                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    if (success) {
                        mCamera.takePicture(null, null, pictureCallback);
                    }
                }
            });
        }
    }
}
