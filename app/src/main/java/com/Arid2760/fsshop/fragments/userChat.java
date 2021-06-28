package com.Arid2760.fsshop.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.Arid2760.fsshop.R;
import com.Arid2760.fsshop.gertterSetter.GetProductData;
import com.Arid2760.fsshop.gertterSetter.GetUserData;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class userChat extends Fragment {
    private TextView userName;
    private ImageButton backBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_chat, container, false);
        init(v);
        BottomNavigationView navBar = getActivity().findViewById(R.id.bottomNav_view);
        navBar.animate().translationY(200);
        GetUserData data = new GetUserData();
        data = (GetUserData) getArguments().getSerializable("CRD");
        userName.setText(data.getUserName());

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navBar.animate().translationY(0);
                transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.fade_out);
                if (getFragmentManager().getBackStackEntryCount() != 0) {
                    getFragmentManager().popBackStack();
                }
            }
        });

        return v;
    }

    void init(View view) {
        userName = view.findViewById(R.id.user_name);
        backBtn = view.findViewById(R.id.backBtn);
    }
}