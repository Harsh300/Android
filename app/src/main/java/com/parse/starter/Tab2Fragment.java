package com.parse.starter;

import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.OutputStreamWriter;

/**
 * Created by Onique on 2017-11-29.
 */

public class Tab2Fragment extends Fragment {
    public static final String TAG = "Tab2Fragment";
    private Button save;
    private EditText note;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2_fragment,container,false);
        //save = (Button)view.findViewById(R.id.saveNote);
        //note = (EditText) view.findViewById(R.id.editText);


        return view;
    }

}
