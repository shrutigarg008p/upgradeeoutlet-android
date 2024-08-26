package com.eoutlet.Eoutlet.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


public class HelveticaNue75BoldTextview extends androidx.appcompat.widget.AppCompatTextView

    {

    public HelveticaNue75BoldTextview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public HelveticaNue75BoldTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HelveticaNue75BoldTextview(Context context) {
        super(context);
        init();
    }

    private void init() {

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "HelveticaNue75.ttf");

        setTypeface(tf);
    }
}