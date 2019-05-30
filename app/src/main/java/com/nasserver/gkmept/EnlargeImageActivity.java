package com.nasserver.gkmept;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class EnlargeImageActivity extends AppCompatActivity {

    private ImageView imageViewEnLargeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enlarge_image);
        imageViewEnLargeImage=(ImageView) findViewById(R.id.imageviewEnLargeImage);
        Intent getIntent=getIntent();
        String path=getIntent.getStringExtra("ImagePath");
        Glide.with(this).load(path).into(imageViewEnLargeImage);
        imageViewEnLargeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.finishAfterTransition(EnlargeImageActivity.this);
            }
        });
    }
}
