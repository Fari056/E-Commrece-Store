package com.Arid2760.fsshop.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.Arid2760.fsshop.DatabaseHelper;
import com.Arid2760.fsshop.Home_Page;
import com.Arid2760.fsshop.R;
import com.Arid2760.fsshop.SessionManagement;
import com.Arid2760.fsshop.gertterSetter.GetUserData;
import com.Arid2760.fsshop.login;
import com.Arid2760.fsshop.updateUser;

import java.util.ArrayList;

public class Profile extends Fragment {
    Button logout;
    TextView currentUser;
    SessionManagement sessionManagement;
    DatabaseHelper helper;
    GetUserData data;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        init(root);
        try {
            helper = new DatabaseHelper(getActivity());
            data = new GetUserData();
            sessionManagement = new SessionManagement(getActivity());
            ArrayList<String> CurrentUInfo = sessionManagement.get_userInformation();
            String id = CurrentUInfo.get(0);
//                Toast.makeText(getContext(), id, Toast.LENGTH_SHORT).show();
            data = helper.getUserRow(id);
            currentUser.setText(data.getUserName());

        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManagement.set_userLoggedOut(false);
                Intent i = new Intent(getContext(), login.class);
                startActivity(i);
            }
        });


        return root;
    }

    void init(View view) {
        logout = (Button) view.findViewById(R.id.userlogoutBtn);
        currentUser = (TextView) view.findViewById(R.id.currentUName);
    }
}