package com.eoutlet.Eoutlet.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

public class HelveticaNueAlticArabicTextView extends androidx.appcompat.widget.AppCompatTextView

{

    public HelveticaNueAlticArabicTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public HelveticaNueAlticArabicTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HelveticaNueAlticArabicTextView(Context context) {
        super(context);
        init();
    }

    private void init() {

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "HelveticaLight.ttf");

        setTypeface(tf);
    }
}

