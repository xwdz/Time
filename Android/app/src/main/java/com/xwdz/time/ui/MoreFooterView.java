/*
 *
 *  * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 *
 */

package com.xwdz.time.ui;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;

import com.xwdz.time.R;


public class MoreFooterView extends AppCompatTextView {
    public MoreFooterView(Context context) {
        super(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                getResources().getDimensionPixelSize(R.dimen.height_footer_view));
        params.gravity = Gravity.CENTER;
        setLayoutParams(params);
        setText(R.string.no_more);
        setGravity(Gravity.CENTER);
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        setTextColor(ResourcesCompat.getColor(getResources(), android.R.color.black, null));
    }
}
