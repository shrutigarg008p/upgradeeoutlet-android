package com.eoutlet.Eoutlet.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class MonestarteBoldTextView extends androidx.appcompat.widget.AppCompatTextView {

    public MonestarteBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MonestarteBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MonestarteBoldTextView(Context context) {
        super(context);
        init();
    }

    private void init() {

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "MonestarateBold.ttf");

        setTypeface(tf);
    }
}
