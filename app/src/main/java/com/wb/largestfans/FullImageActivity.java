package com.wb.largestfans;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;



import com.squareup.picasso.Picasso;
import com.wb.largestfans.utility.RoundedTransformation;

public class FullImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        ImageView imgCross = (ImageView) findViewById(R.id.imgCross);
        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView imgFull = (ImageView) findViewById(R.id.imgFull);
        String imageUrl = getIntent().getStringExtra("imageUrl");

        Picasso.with(this)
                .load(imageUrl)
                .error(R.mipmap.ic_launcherno)
                .transform(new RoundedTransformation(20, 0))
                .fit()
                .centerCrop()
                .into(imgFull);

    }
}