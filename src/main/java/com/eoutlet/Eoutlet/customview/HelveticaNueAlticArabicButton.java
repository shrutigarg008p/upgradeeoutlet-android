package com.eoutlet.Eoutlet.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

public class HelveticaNueAlticArabicButton extends androidx.appcompat.widget.AppCompatButton

{

    public HelveticaNueAlticArabicButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public HelveticaNueAlticArabicButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HelveticaNueAlticArabicButton(Context context) {
        super(context);
        init();
    }

    private void init() {

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "HelveticaLight.ttf");

        setTypeface(tf);
    }
}

