package com.eoutlet.Eoutlet.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


public class HelveticaBoldTextView extends androidx.appcompat.widget.AppCompatTextView

    {

    public HelveticaBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public HelveticaBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HelveticaBoldTextView(Context context) {
        super(context);
        init();
    }

    private void init() {

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "HelveticaNeueBold.ttf");

        setTypeface(tf);
    }
}