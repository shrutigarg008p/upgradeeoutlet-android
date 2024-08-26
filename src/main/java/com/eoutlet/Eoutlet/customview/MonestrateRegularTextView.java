package com.eoutlet.Eoutlet.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class MonestrateRegularTextView extends androidx.appcompat.widget.AppCompatTextView

{

    public MonestrateRegularTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MonestrateRegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MonestrateRegularTextView(Context context) {
        super(context);
        init();
    }

    private void init() {

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "MontserratRegular.ttf");

        setTypeface(tf);
    }}