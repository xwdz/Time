package com.xwdz.time.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.xwdz.time.R;


public class LoadingDialog extends Dialog {


    private ImageView mLoading;
    private TextView  mLabel;
    private String    mText;
    private int       mTextRes;
    private Animation mAnimation;

    public LoadingDialog(Context context) {
        super(context, R.style.loadDialog);
    }


    public LoadingDialog(Context context, Builder builder) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_loading);
        mLoading = (ImageView) findViewById(R.id.lv_loading);
        mLabel = (TextView) findViewById(R.id.tv_load_text);
        setCancelable(false);
        setupAnimation();
    }

    private void setupAnimation() {
        mAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        mLoading.setAnimation(mAnimation);
    }

    @Override
    public void show() {
        super.show();
        if (mAnimation.hasStarted()) {
            mAnimation.cancel();
        }

        mAnimation.start();
    }

    private void setUpText() {
        if (mTextRes > 0) {
            mLabel.setText(mTextRes);
        } else {
            mLabel.setText(mText);
        }
    }


    public void stopLoading() {
        mLoading.clearAnimation();
        this.dismiss();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAnimation != null) {
            mAnimation.cancel();
        }
    }

    public static class Builder {

        public String  mText;
        public int     mTextRes;
        public Context mContext;


        public Builder setText(String text) {
            this.mText = text;
            return this;
        }

        public Builder setText(int textRes) {
            this.mTextRes = textRes;
            return this;
        }

        public LoadingDialog build() {
            return new LoadingDialog(mContext, this);
        }

    }
}
