package com.eoutlet.Eoutlet.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class MulishBoldTextView extends AppCompatTextView

{

    public MulishBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MulishBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MulishBoldTextView(Context context) {
        super(context);
        init();
    }

    private void init() {

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "Mulish_Bold.ttf");

        setTypeface(tf);
    }
}

