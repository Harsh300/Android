package com.parse.starter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

/**
 * Created by Onique on 2017-12-13.
 */

public class MessageListAdapter extends ArrayAdapter {
    private int[] colours = new int[]{Color.parseColor("#F0F0F0"), Color.parseColor("#90EE90")};

    // Constructor
    public MessageListAdapter(Context context, int layout, ArrayList<String> values) {
        super(context, layout, values);
    }

    //Display messages in alternating colours
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        int colourPosition = position % colours.length;
        view.setBackgroundColor(colours[colourPosition]);
        return view;
    }
}
