package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.eoutlet.Eoutlet.R;

public class CustomArrayAdapter extends BaseAdapter {

    Context context;

    String[] countryNames;
    LayoutInflater inflter;

    private int last_checked;

    public CustomArrayAdapter(Context applicationContext, String[] countryNames) {
        this.context = applicationContext;

        this.countryNames = countryNames;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return countryNames.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.dialog_test, null);

        TextView names = (TextView) view.findViewById(R.id.text_lang_box);
        RadioButton radioButton = (RadioButton) view.findViewById(R.id.radio_);

        names.setText(countryNames[i]);
        if (i == last_checked) {
            radioButton.setChecked(true);
        } else
            radioButton.setChecked(false);

        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                last_checked = i;
                notifyDataSetChanged();
            }
        });
        return view;
    }
}