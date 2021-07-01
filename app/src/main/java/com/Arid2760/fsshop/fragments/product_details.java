package com.Arid2760.fsshop.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Arid2760.fsshop.DatabaseHelper;
import com.Arid2760.fsshop.R;
import com.Arid2760.fsshop.adapters.GridViewAdapter;
import com.Arid2760.fsshop.gertterSetter.GetProductData;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class product_details extends Fragment {
    TextView prodName, prodPrice, prodDescription, total;
    ImageView prodImage;
    ImageButton BackToHome, WishList;
    Button cartBtn, buyBtn, minus, plus;
    DatabaseHelper helper;
    int count1 = 1;
    String itemId;
    String totalPrice;
    private String id;
    String priceString;
    String pName;
    String pImgAddress;

    private static final String url = "http://192.168.8.107/FSElect/getProduct.php";


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_product_details, container, false);
        init(root);
        BottomNavigationView navBar = getActivity().findViewById(R.id.bottomNav_view);
        navBar.animate().translationY(200);
        BackToHome.bringToFront();

        id = getArguments().getString("CPid");


/*        helper = new DatabaseHelper(getContext());
        Toast.makeText(getContext(), id, Toast.LENGTH_SHORT).show();
        GetProductData data1 = new GetProductData();
        data1 = helper.getSelectedProduct(id);

        itemId = String.valueOf(data1.getId());
        prodName.setText(data1.getName());
        prodPrice.setText(data1.getPrice());
        prodDescription.setText(data1.getDescription());
        prodImage.setImageBitmap(data1.getImageBitmap());*/

        GetProductData d = new GetProductData();
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject result = new JSONObject(response);
                        for (int i = 0; i <= result.length(); i++) {
                            String id = result.getString("id");
                            String name = result.getString("name");
                            String price = result.getString("price");
                            String description = result.getString("description");
                            String imgUrl = result.getString("image");
                            String imageAddress = "http://192.168.8.107/FSElect/images/" + imgUrl;

                            pName = name;
                            pImgAddress = imageAddress;

                            total.setText(price);
                            prodName.setText(name);
                            prodPrice.setText(price);
                            prodDescription.setText(description);
                            imageLoadTask obj = new imageLoadTask(imageAddress, prodImage);
                            obj.execute();
                            priceString = price;
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


        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        BackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navBar.animate().translationY(0);
                transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.fade_out);
                if (getFragmentManager().getBackStackEntryCount() != 0) {
                    getFragmentManager().popBackStack();
                }
            }
        });


        //Total price Counter Start


        final TextView count = (TextView) root.findViewById(R.id.count);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int price = Integer.parseInt(priceString.trim());
                total.setText(prodPrice.getText().toString());
                totalPrice = String.valueOf(price);
                count1++;
                count.setText(String.valueOf(count1));
                int totalP = price * count1;
                totalPrice = String.valueOf(totalP);
                total.setText("RS: " + String.valueOf(totalP));
//                Toast.makeText(getContext(), newPr, Toast.LENGTH_SHORT).show();
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int price = Integer.parseInt(priceString.trim());
                if (count1 <= 1) {
                    Toast.makeText(getContext(), "Quantity cannot be less then 1", Toast.LENGTH_SHORT).show();

                } else {
                    count1--;
                    count.setText(String.valueOf(count1));
                    int totalP = price * count1;
                    totalPrice = String.valueOf(totalP);
                    total.setText("RS: " + String.valueOf(totalP));
                }
            }
        });
//Total price Counter End

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSharedPreferences("add_to_cart", Context.MODE_PRIVATE).edit().putString("itemId", itemId).apply();
                requireActivity().getSharedPreferences("add_to_cart", Context.MODE_PRIVATE).edit().putString("totalPrice", totalPrice).apply();
            }
        });

        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyNow buyNow = new buyNow();
                transaction.replace(R.id.navHostFragment, buyNow);
                Bundle bundle = new Bundle();
                bundle.putString("name", pName);
                bundle.putString("img", pImgAddress);
                bundle.putString("Price", totalPrice);
                buyNow.setArguments(bundle);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return root;
    }

    void init(View view) {
        prodName = view.findViewById(R.id.productName);
        prodPrice = view.findViewById(R.id.productPrice);
        prodDescription = view.findViewById(R.id.productDescription);
        prodImage = view.findViewById(R.id.productImg);
        BackToHome = view.findViewById(R.id.backToHomeBtn);
        WishList = view.findViewById(R.id.wishList);
        cartBtn = view.findViewById(R.id.AddToCArt);
        buyBtn = view.findViewById(R.id.buyNow);

        minus = (Button) view.findViewById(R.id.minus);
        plus = (Button) view.findViewById(R.id.plus);
        total = (TextView) view.findViewById(R.id.totalPrice);
    }

    class imageLoadTask extends AsyncTask<Void, Void, Bitmap> {
        private String url1;
        private ImageView imageView1;

        public imageLoadTask(String imageBitmap, ImageView tt3) {
            this.url1 = imageBitmap;
            this.imageView1 = tt3;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            try {
                URL connection = new URL(url1);
                InputStream inputStream = connection.openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Bitmap newbit = Bitmap.createScaledBitmap(bitmap, 400, 400, true);
                return newbit;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView1.setImageBitmap(bitmap);
        }
    }
}