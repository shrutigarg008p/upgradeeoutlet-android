package com.eoutlet.Eoutlet.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


public class Helvetica55Roman extends androidx.appcompat.widget.AppCompatTextView

    {

    public Helvetica55Roman(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Helvetica55Roman(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Helvetica55Roman(Context context) {
        super(context);
        init();
    }

    private void init() {

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "Helvetica55Roman.ttf");

        setTypeface(tf);
    }
}