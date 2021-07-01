package com.Arid2760.fsshop.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.Arid2760.fsshop.DatabaseHelper;
import com.Arid2760.fsshop.R;
import com.Arid2760.fsshop.SessionManagement;
import com.Arid2760.fsshop.gertterSetter.GetUserData;
import com.Arid2760.fsshop.login;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Profile extends Fragment {

    TextView currentUser;
    SessionManagement sessionManagement;
    Toolbar toolbar;
    //    DatabaseHelper helper;
    GetUserData data;
    String id;
    private static final String url = "http://192.168.8.107/FSElect/rowdata.php";

    public Profile() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        init(root);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.bringToFront();
        try {
//            helper = new DatabaseHelper(getActivity());

//            data = new GetUserData();
//            data = (GetUserData) getActivity().getIntent().getSerializableExtra("data");
            sessionManagement = new SessionManagement(getActivity());
            ArrayList<String> CurrentUInfo = sessionManagement.get_userInformation();
            id = CurrentUInfo.get(0);
//                Toast.makeText(getContext(), id, Toast.LENGTH_SHORT).show();
//            data = helper.getUserRow(id);
//            currentUser.setText(data.getUserName());

            try {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject result = new JSONObject(response);
                            for (int i = 0; i <= result.length(); i++) {
                                String name = result.getString("name");
                                currentUser.setText(name);
                            }

                        } catch (Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> data = new HashMap<>();
                        data.put("id", id);
                        return data;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);
            } catch (Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }


//            currentUser.setText(data.getUserName());


        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }



        return root;
    }

    void init(View view) {

        currentUser = (TextView) view.findViewById(R.id.currentUName);
        toolbar = view.findViewById(R.id.toolbar);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.profile_top_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment newFragment;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (id == R.id.action_settings) {

            newFragment = new Settings();
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            transaction.replace(R.id.navHostFragment, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        return super.onOptionsItemSelected(item);
    }
}