package com.gy;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CameraActivity extends AppCompatActivity {

    private SurfaceView sv_camera_surfaceview;

    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_send);
        sv_camera_surfaceview = (SurfaceView) findViewById(R.id.sv_camera_surfaceview);
        sv_camera_surfaceview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //打开照相机
                camera = Camera.open();
                //给照相机设置参数
                Camera.Parameters parameters = camera.getParameters();
                //设置保存格式
                parameters.setPictureFormat(PixelFormat.JPEG);
                //设置质量
                parameters.set("jpeg-quality", 85);
                //给照相机设置参数
                camera.setParameters(parameters);
                //将照相机捕捉的画面展示到SurfaceView
                try {
                    camera.setPreviewDisplay(sv_camera_surfaceview.getHolder());
                    //开启预览
                    camera.startPreview();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    //拍照
    public void takePhoto(View view) {
        camera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                //将字节数组
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                //输出流保存数据
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream("/mnt/sdcard/DCIM/camera/LuoMei\"+System.currentTimeMillis()+\".png");
                    bitmap.compress(Bitmap.CompressFormat.PNG, 85, fileOutputStream);
                    camera.stopPreview();
                    camera.startPreview();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}