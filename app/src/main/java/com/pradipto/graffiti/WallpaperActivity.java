package com.pradipto.graffiti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class WallpaperActivity extends AppCompatActivity {

    Intent intent;
    ImageView wallpaperImg;
    FloatingActionButton setButton, downloadBtn, shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);

        wallpaperImg = findViewById(R.id.idPhoto);
        setButton = findViewById(R.id.settingFab);
        downloadBtn = findViewById(R.id.dwnldFab);
        shareButton = findViewById(R.id.shareFab);

        intent = getIntent();
        String url = intent.getStringExtra("original");
        Picasso.get().load(url).placeholder(R.drawable.ic_placeholder).into(wallpaperImg);

        final WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        setButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    Bitmap bitmap = ((BitmapDrawable) wallpaperImg.getDrawable()).getBitmap();
                    wallpaperManager.setBitmap(bitmap);
                    Toast.makeText(WallpaperActivity.this, "Wallpaper Applied", Toast.LENGTH_SHORT).show();
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        final DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse(url);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setTitle("Wallpapers by Pexels | Graffiti")
                        .setMimeType("image/jpeg")
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Wallpapers by Pexels | Graffiti.jpg");
                downloadManager.enqueue(request);
                Toast.makeText(WallpaperActivity.this, "Download Complete", Toast.LENGTH_SHORT).show();
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bitmap bitmap = ((BitmapDrawable) wallpaperImg.getDrawable()).getBitmap();
                String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Wallpapers by Pexels | Graffiti", null);
                Uri uri = Uri.parse(bitmapPath);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.putExtra(Intent.EXTRA_TEXT, "https://www.pexels.com/");
                startActivity(Intent.createChooser(intent, "share via"));
            }
        });
    }
}
