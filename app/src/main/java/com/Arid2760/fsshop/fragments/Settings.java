package com.Arid2760.fsshop.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.Arid2760.fsshop.DatabaseHelper;
import com.Arid2760.fsshop.R;
import com.Arid2760.fsshop.SessionManagement;
import com.Arid2760.fsshop.gertterSetter.GetUserData;
import com.Arid2760.fsshop.login;
import com.Arid2760.fsshop.updateUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Settings extends Fragment {

    Button logout;
    private String newId;
    TextView updateProfile, contact_us;
    ImageButton backBtn;
    SessionManagement sessionManagement;
    DatabaseHelper helper;
    GetUserData data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        init(root);
        BottomNavigationView navBar = getActivity().findViewById(R.id.bottomNav_view);
        navBar.animate().translationY(200);
/*        try {
            helper = new DatabaseHelper(getActivity());
           data = new GetUserData();
            sessionManagement = new SessionManagement(getActivity());
            ArrayList<String> CurrentUInfo = sessionManagement.get_userInformation();
            newId = CurrentUInfo.get(0);

        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
                    String id = newId;
                    GetUserData Data = (GetUserData) databaseHelper.getUserRow(id);
                    Intent in = new Intent(getContext(), updateUser.class);
                    Bundle b = new Bundle();
                    b.putSerializable("NData", Data);
                    in.putExtras(b);
                    startActivity(in);
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });*/
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navBar.animate().translationY(0);
                transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.fade_out);
                if (getFragmentManager().getBackStackEntryCount() != 0) {
                    getFragmentManager().popBackStack();
                }
//                transaction.replace(R.id.navHostFragment, new Home());
//                transaction.addToBackStack(null);
//                transaction.commit();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManagement.set_userLoggedOut(false);
                Intent i = new Intent(getContext(), login.class);
                startActivity(i);
            }
        });

        contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction.replace(R.id.navHostFragment, new contact_us());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return root;
    }

    void init(View view) {
        logout = (Button) view.findViewById(R.id.userlogoutBtn);
        backBtn = view.findViewById(R.id.backBtn);
        updateProfile = (TextView) view.findViewById(R.id.updateProfile);
        contact_us = (TextView) view.findViewById(R.id.contact);
    }

}