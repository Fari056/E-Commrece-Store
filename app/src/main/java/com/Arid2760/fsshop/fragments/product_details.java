package com.Arid2760.fsshop.fragments;

import android.content.Context;
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
import com.Arid2760.fsshop.gertterSetter.GetProductData;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_product_details, container, false);
        init(root);
        BottomNavigationView navBar = getActivity().findViewById(R.id.bottomNav_view);
        navBar.animate().translationY(200);

        helper = new DatabaseHelper(getContext());
        BackToHome.bringToFront();
        GetProductData d = new GetProductData();
        String id = getArguments().getString("CPid");
//        Toast.makeText(getContext(), id, Toast.LENGTH_SHORT).show();
        GetProductData data1 = new GetProductData();
        data1 = helper.getSelectedProduct(id);

        itemId = String.valueOf(data1.getId());
        prodName.setText(data1.getName());
        prodPrice.setText(data1.getPrice());
        prodDescription.setText(data1.getDescription());
        prodImage.setImageBitmap(data1.getImageBitmap());

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

        String priceString = prodPrice.getText().toString();
        int price = Integer.parseInt(priceString.substring(3).trim());
        total.setText(prodPrice.getText().toString());
        final TextView count = (TextView) root.findViewById(R.id.count);
        totalPrice = String.valueOf(price);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
}