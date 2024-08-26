package com.eoutlet.Eoutlet.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

public class MulishRegularButton extends AppCompatButton

{

    public MulishRegularButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MulishRegularButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MulishRegularButton(Context context) {
        super(context);
        init();
    }

    private void init() {

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "Mulish_Regular.ttf");

        setTypeface(tf);
    }
}

