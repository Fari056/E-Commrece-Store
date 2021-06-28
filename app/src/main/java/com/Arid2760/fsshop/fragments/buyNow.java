package com.Arid2760.fsshop.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Arid2760.fsshop.DatabaseHelper;
import com.Arid2760.fsshop.R;
import com.Arid2760.fsshop.SessionManagement;
import com.Arid2760.fsshop.gertterSetter.GetProductData;
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

public class buyNow extends Fragment {
    private TextView name1, phone1, email1, totalPrice1, productName1;
    private ImageView productImg;
    private ImageButton backBtn;
    String id;
    SessionManagement sessionManagement;
    DatabaseHelper helper;

    private static final String url = "http://192.168.8.100/FSElect/rowdata.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_buy_now, container, false);
        init(v);


        helper = new DatabaseHelper(getContext());
        String price = getArguments().getString("Price");
        totalPrice1.setText(price);
        String Cid = getArguments().getString("Cid");
        GetProductData data1 = new GetProductData();
        data1 = helper.getSelectedProduct(Cid);
        productName1.setText(data1.getName());
        productImg.setImageBitmap(data1.getImageBitmap());


        Toast.makeText(getContext(), Cid + "  " + price, Toast.LENGTH_LONG).show();

        sessionManagement = new SessionManagement(getActivity());
        ArrayList<String> CurrentUInfo = sessionManagement.get_userInformation();
        id = CurrentUInfo.get(0);

        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject result = new JSONObject(response);
                        for (int i = 0; i <= result.length(); i++) {
                            String name = result.getString("name");
                            String email = result.getString("email");
                            String phone = result.getString("phone");
                            name1.setText(name);
                            email1.setText(email);
                            phone1.setText(phone);
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

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager().getBackStackEntryCount() != 0) {
                    getFragmentManager().popBackStack();
                }
            }
        });

        return v;
    }

    void init(View v) {
        name1 = v.findViewById(R.id.nameBuy);
        phone1 = v.findViewById(R.id.phoneBuy);
        email1 = v.findViewById(R.id.emailBuy);
        productImg = v.findViewById(R.id.productImage);
        totalPrice1 = v.findViewById(R.id.productPrice1);
        productName1 = v.findViewById(R.id.productName12);
        backBtn = v.findViewById(R.id.backBtn);
    }
}