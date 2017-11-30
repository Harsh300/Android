package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Onique on 2017-11-29.
 */

public class Tab3Fragment extends Fragment {
    public static final String TAG = "Tab3Fragment";
    private Button btnTEST3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3_fragment,container,false);
        btnTEST3 = (Button) view.findViewById(R.id.btnTEST3);
        btnTEST3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "TESTING BUTTON CLICK 3", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), UserListActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
