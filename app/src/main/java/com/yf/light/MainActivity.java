package com.yf.light;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static Camera camera;
    private  static Camera.Parameters parameters;
    private boolean status=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.ctl).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.ctl)
        {
            if(!status)
            {
                MainActivity.openLight();
                status=true;
            }
            else
            {
                MainActivity.closeLight();
                status=false;
            }
        }
    }
    protected static void openLight() {
        try {
            camera = Camera.open();
            int textureId = 0;
            camera.setPreviewTexture(new SurfaceTexture(textureId));
            camera.startPreview();
            parameters = camera.getParameters();
            parameters.setFlashMode(parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
        } catch (Exception e) {
            Log.i("打开闪光灯失败：",e.toString()+"");
        }
    }

    protected static void closeLight() {

        if (camera != null) {
            parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }
}
