package com.xwdz.time;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xwdz.time.entity.Picture;

/**
 * @author xingwei.huang (xwdz9989@gamil.com)
 * @since 2019/3/29
 */
public class DetailActivity extends AppCompatActivity {


    private static final String BASE_STATIC_URL = "http://47.106.223.246/uploads/";

    private Picture mPicture;

    private ImageView mImageView;
    private TextView  mDesc;
    private TextView  mAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mImageView = findViewById(R.id.picture);
        mDesc = findViewById(R.id.desc);
        mAddress = findViewById(R.id.address);


        final Intent intent = getIntent();
        if (intent != null) {
            mPicture = (Picture) intent.getSerializableExtra("info");


            mDesc.setText(mPicture.getDesc());
            mAddress.setText(mPicture.getAddress());
            ImageLoader.load(this, BASE_STATIC_URL + mPicture.getName(), mImageView);
        }
    }

    public static void start(Context context, Picture picture) {
        Intent starter = new Intent(context, DetailActivity.class);
        starter.putExtra("info", picture);
        context.startActivity(starter);
    }
}
