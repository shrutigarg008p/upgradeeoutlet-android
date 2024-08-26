package com.eoutlet.Eoutlet.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CompoundButton;

import androidx.appcompat.widget.AppCompatRadioButton;

public class MyMulishRadioButton extends AppCompatRadioButton {


    private OnCheckedChangeListener onCheckedChangeListener;


    public MyMulishRadioButton(Context context) {
        super(context);
    }

    public MyMulishRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyMulishRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setOwnOnCheckedChangeListener();
        setButtonDrawable(null);//lets remove the default drawable to create our own

    }
    public void setOwnOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    private void setOwnOnCheckedChangeListener() {
        setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (onCheckedChangeListener != null) {
                    //this is called when we have set our listener
                    onCheckedChangeListener.onCheckedChanged(buttonView, isChecked);
                }
            }
        });
    }
    private void init() {

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "Mulish_Bold.ttf");

        setTypeface(tf);
    }

}