package com.Arid2760.fsshop.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.Arid2760.fsshop.R;
import com.Arid2760.fsshop.adapters.listChatAdapter;
import com.Arid2760.fsshop.gertterSetter.GetUserData;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Chats extends Fragment {
    ListView listView1, listView2;
    GetUserData data;
    List<GetUserData> list;
    private static final String url = "http://192.168.8.107/FSElect/allUsers.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_chats, container, false);
        init(v);
        listView1.setDivider(null);
        listView1.setDividerHeight(0);


        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("Failed")) {
                        Toast.makeText(getContext(), "Credentials are not valid ", Toast.LENGTH_SHORT).show();
                    } else {
                        list = new ArrayList<>();
                        try {
                            JSONArray res = new JSONArray(response);
                            for (int i = 0; i < res.length(); i++) {
                                JSONObject result = res.getJSONObject(i);
                                String id = result.getString("id");
                                String name = result.getString("name");
//                                String email = result.getString("email");
//                                String password = result.getString("password");
                                String phone = result.getString("phone");
//                                String DOB = result.getString("DOB");
//                                String gender = result.getString("gender");

                                data = new GetUserData();
                                data.setId(Integer.parseInt(id));
                                data.setUserName(name);
//                                data.setUserEmail(email);
//                                data.setUserPassword(password);
                                data.setUserPhone(phone);
//                                data.setUserDOB(DOB);
//                                data.setUserGender(gender);
                                list.add(data);
                            }
                            if (list.size() != 0) {
                                listChatAdapter customAdapter = new listChatAdapter(getContext(), R.layout.user_chat_view, list);
                                listView1.setAdapter(customAdapter);
                            }

                        } catch (Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
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
                    return super.getParams();
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    GetUserData currentRow = (GetUserData) parent.getItemAtPosition(position);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.fade_out);
                    userChat userChat = new userChat();
                    transaction.replace(R.id.navHostFragment, userChat);
                    transaction.addToBackStack(null);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("CRD", currentRow);
                    userChat.setArguments(bundle);
                    transaction.commit();

//                currentRow.getId();
//                    Toast.makeText(getContext(), String.valueOf(currentRow.getId()), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {

                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    void init(View view) {
        listView1 = (ListView) view.findViewById(R.id.listChats);
    }
}