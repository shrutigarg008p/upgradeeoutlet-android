package com.eoutlet.Eoutlet.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

public class MulishRegularEdittext extends AppCompatEditText {

    public MulishRegularEdittext(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MulishRegularEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MulishRegularEdittext(Context context) {
        super(context);
        init();
    }

    private void init() {

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "Mulish_Regular.ttf");

        setTypeface(tf);
    }
}