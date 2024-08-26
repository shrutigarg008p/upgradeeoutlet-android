package com.eoutlet.Eoutlet.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class HelveticaRegularTextView extends androidx.appcompat.widget.AppCompatTextView

    {

    public HelveticaRegularTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public HelveticaRegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HelveticaRegularTextView(Context context) {
        super(context);
        init();
    }

    private void init() {

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "HelveticaLight.ttf");

        setTypeface(tf);
    }
}