package com.eoutlet.Eoutlet.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

public class HalveticRegularEditText extends AppCompatEditText

{

    public HalveticRegularEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public HalveticRegularEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HalveticRegularEditText(Context context) {
        super(context);
        init();
    }

    private void init() {

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "HelveticaNeue.ttf");

        setTypeface(tf);
    }
}

