package com.stoor.navigationbar;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */

public class ProfileFragment extends Fragment {

    FloatingActionButton editButtonProfile;
    EditText majorValueProfile, emailProfile, numberProfile;
    boolean editable = false;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        editButtonProfile = (FloatingActionButton) view.findViewById(R.id.editButtonProfile);
        majorValueProfile = (EditText) view.findViewById(R.id.majorValueProfile);
        emailProfile = (EditText) view.findViewById(R.id.emailProfile);
        numberProfile = (EditText) view.findViewById(R.id.numberProfile);


        editButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editable == false) {

                    Log.i("info", "Enabled");
                    editButtonProfile.setImageResource(R.drawable.ic_check_circle_black_24dp);
                    majorValueProfile.setEnabled(true);
                    emailProfile.setEnabled(true);
                    numberProfile.setEnabled(true);
                    editable = true;

                } else {

                    Log.i("info","Disabled");
                    editButtonProfile.setImageResource(R.drawable.ic_edit_black_24dp);
                    majorValueProfile.setEnabled(false);
                    emailProfile.setEnabled(false);
                    numberProfile.setEnabled(false);
                    editable = false;

                }

            }
        });

        return view;
    }
}
