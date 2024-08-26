package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.eoutlet.Eoutlet.R;

import java.util.ArrayList;

public class SearchResultAdapter extends ArrayAdapter {

ArrayList name ;

    public SearchResultAdapter(Context context, ArrayList<String> users) {
        super(context, 0, users);
        name = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_textview, parent, false);
        }


        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.searchtext);
        tvName.setText(name.get(position).toString());

        return convertView;
    }



    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }




}
