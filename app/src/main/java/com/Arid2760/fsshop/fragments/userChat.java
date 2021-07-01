package com.Arid2760.fsshop.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.Arid2760.fsshop.R;
import com.Arid2760.fsshop.SessionManagement;
import com.Arid2760.fsshop.adapters.customMessagesList;
import com.Arid2760.fsshop.adapters.listChatAdapter;
import com.Arid2760.fsshop.gertterSetter.GetMessageData;
import com.Arid2760.fsshop.gertterSetter.GetProductData;
import com.Arid2760.fsshop.gertterSetter.GetUserData;
import com.Arid2760.fsshop.login;
import com.Arid2760.fsshop.signUp;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class userChat extends Fragment {
    private TextView userName;
    private ImageButton backBtn;
    private EditText messageTxt;
    private Button sndMsgBtn;
    GetUserData data;
    GetMessageData msgs;
    SessionManagement sessionManagement;
    List<GetMessageData> list1;
    private ListView listView, listView2;

    private static final String sendingUrl = "http://192.168.8.107/FSElect/saveMessage.php";
    private static final String messageUrl = "http://192.168.8.107/FSElect/getMessages.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_chat, container, false);
        init(v);
        listView.setDivider(null);
        listView.setDividerHeight(0);
        listView2.setDivider(null);
        listView2.setDividerHeight(0);
        BottomNavigationView navBar = getActivity().findViewById(R.id.bottomNav_view);
        navBar.animate().translationY(200);
        data = new GetUserData();
        data = (GetUserData) getArguments().getSerializable("CRD");
        userName.setText(data.getUserName());


        //send messages to database start
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, messageUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
//                    Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                    list1 = new ArrayList<>();
                    try {
                        JSONArray res = new JSONArray(response);
                        for (int i = 0; i < res.length(); i++) {
                            JSONObject result = res.getJSONObject(i);
                            String messages = result.getString("message");
//                            Toast.makeText(getActivity(), messages, Toast.LENGTH_SHORT).show();
                            msgs = new GetMessageData();
                            msgs.setMessage(messages);
                            list1.add(msgs);
                        }
                        if (list1.size() != 0) {
                            customMessagesList customAdapter = new customMessagesList(getContext(), R.layout.msg_view, list1);
                            listView.setAdapter(customAdapter);
                        }
                    } catch (
                            Exception e) {
                        Toast.makeText(getContext(), "nooo", Toast.LENGTH_SHORT).show();
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
                    Map<String, String> data1 = new HashMap<>();
                    sessionManagement = new SessionManagement(getActivity());
                    ArrayList<String> CurrentUInfo = sessionManagement.get_userInformation();
                    String senderId = CurrentUInfo.get(0);
                    String receiverId = String.valueOf(data.getId());
                    data1.put("sId", senderId);
                    data1.put("rId", receiverId);
                    return data1;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);
        } catch (
                Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }  //send messages to database ends

//receiver messages from database start
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, messageUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
//                    Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                    list1 = new ArrayList<>();
                    try {
                        JSONArray res = new JSONArray(response);
                        for (int i = 0; i < res.length(); i++) {
                            JSONObject result = res.getJSONObject(i);
                            String messages = result.getString("message");
//                            Toast.makeText(getActivity(), messages, Toast.LENGTH_SHORT).show();
                            msgs = new GetMessageData();
                            msgs.setMessage(messages);
                            list1.add(msgs);
                        }
                        if (list1.size() != 0) {
                            customMessagesList customAdapter = new customMessagesList(getContext(), R.layout.rcvr_msg_view, list1);
                            listView2.setAdapter(customAdapter);
                        }
                    } catch (
                            Exception e) {
                        Toast.makeText(getContext(), "nooo", Toast.LENGTH_SHORT).show();
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
                    Map<String, String> data1 = new HashMap<>();
                    sessionManagement = new SessionManagement(getActivity());
                    ArrayList<String> CurrentUInfo = sessionManagement.get_userInformation();
                    String senderId = CurrentUInfo.get(0);
                    String receiverId = String.valueOf(data.getId());
                    data1.put("sId", receiverId);
                    data1.put("rId", senderId);
                    return data1;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);
        } catch (
                Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }   //receiver messages from database ends


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
        sndMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManagement = new SessionManagement(getActivity());
                ArrayList<String> CurrentUInfo = sessionManagement.get_userInformation();
                String senderId = CurrentUInfo.get(0);
                String receiverId = String.valueOf(data.getId());
                String message = messageTxt.getText().toString();
//                Toast.makeText(getContext(), "Sender ID " + senderId + " Receiver ID " + receiverId + " Message " + message, Toast.LENGTH_LONG).show();

                if (!senderId.equals("") && !receiverId.equals("") && !message.equals("")) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, sendingUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                            if (response.equals("Success")) {
                                Toast.makeText(getActivity(), "Message Sent", Toast.LENGTH_SHORT).show();
                            } else if (response.equals("Failed")) {
                                Toast.makeText(getActivity(), "Try again latter", Toast.LENGTH_SHORT).show();
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
                            data.put("senderId", senderId);
                            data.put("receiverId", receiverId);
                            data.put("message", message);
                            return data;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                    requestQueue.add(stringRequest);

                } else
                    Toast.makeText(getActivity(), "Please Fill all the fields", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    void init(View view) {
        userName = view.findViewById(R.id.user_name);
        backBtn = view.findViewById(R.id.backBtn);
        sndMsgBtn = view.findViewById(R.id.msgBtn1);
        messageTxt = view.findViewById(R.id.msgTxt);
        listView = (ListView) view.findViewById(R.id.listChat);
        listView2 = (ListView) view.findViewById(R.id.listChat1);
    }
}