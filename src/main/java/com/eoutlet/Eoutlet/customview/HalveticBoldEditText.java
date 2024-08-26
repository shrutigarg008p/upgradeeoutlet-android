package com.eoutlet.Eoutlet.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

public class HalveticBoldEditText extends AppCompatEditText

{

    public HalveticBoldEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public HalveticBoldEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HalveticBoldEditText(Context context) {
        super(context);
        init();
    }

    private void init() {

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "HelveticaNeueBold.ttf");

        setTypeface(tf);
    }
}

