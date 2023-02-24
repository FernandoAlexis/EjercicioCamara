package com.android.ejerciciocamara;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;

public class MainActivity extends AppCompatActivity {

    TextView captureTxt;
    String path;
    Uri uri;
    private ImageView captureImage,sendWhatssap,sendCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        sendWhatssap = findViewById(R.id.send);
        sendCorreo = findViewById(R.id.correo);
        captureTxt = findViewById(R.id.idEventBrowse);
        captureImage = findViewById(R.id.my_avatar);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }
        }
        sendWhatssap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setType("image/*");

                if(uri != null){
                    sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    try {
                        startActivity(sendIntent);
                        Toast.makeText(MainActivity.this, "No se ha seleccionado una imagen"+uri,Toast.LENGTH_LONG).show();

                    }catch (Exception e){
                        Toast.makeText(MainActivity.this, "Error al enviar\n"+e.getMessage(),Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(MainActivity.this, "No se ha seleccionado una imagen",Toast.LENGTH_LONG).show();
                }
            }
        });
        captureTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImagePicker.Companion.with(MainActivity.this)
                        .crop()
                        .maxResultSize(180,1080)
                        .start(101);


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            captureImage.setImageURI(uri);

        }

    }


}