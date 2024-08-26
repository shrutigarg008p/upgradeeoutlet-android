package com.eoutlet.Eoutlet.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class MonestarteSemiBoldTextView extends androidx.appcompat.widget.AppCompatTextView {

    public MonestarteSemiBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MonestarteSemiBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MonestarteSemiBoldTextView(Context context) {
        super(context);
        init();
    }

    private void init() {

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "MontserratSemiBold.ttf");

        setTypeface(tf);
    }
}
