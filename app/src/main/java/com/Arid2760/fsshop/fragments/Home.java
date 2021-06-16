package com.Arid2760.fsshop.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.Arid2760.fsshop.DatabaseHelper;
import com.Arid2760.fsshop.adapters.GridViewAdapter;
import com.Arid2760.fsshop.R;
import com.Arid2760.fsshop.adapters.SliderAdapter;
import com.Arid2760.fsshop.gertterSetter.GetProductData;
import com.Arid2760.fsshop.gertterSetter.SliderData;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {
    ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
    GridView gridView;
    Toolbar toolbar;
    DatabaseHelper databaseHelper;
    SliderView sliderView;

    // Urls of our images.
    String url1 = "https://www.indiewire.com/wp-content/uploads/2019/06/00_Best-TV-Posters-of-2019.jpg?w=780";
    String url2 = "https://static-cse.canva.com/image/96812/posters.jpg";
    String url3 = "https://i.pinimg.com/originals/bc/2d/d0/bc2dd09c456f394ede98fe956f2ad6d8.jpg";


    public Home() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        init(root);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        //Image Slider Start

        // initializing the slider view.

        // adding the urls inside array list
        sliderDataArrayList.add(new SliderData(url1));
        sliderDataArrayList.add(new SliderData(url2));
        sliderDataArrayList.add(new SliderData(url3));
        // passing this array list inside our adapter class.
        SliderAdapter adapter = new SliderAdapter(getActivity(), sliderDataArrayList);
        // right direction you can change according to requirement.
        sliderView.getAutoCycleDirection();
        // below method is used to
        // setadapter to sliderview.
        sliderView.setSliderAdapter(adapter);
        // below method is use to set
        // scroll time in seconds.
        sliderView.setScrollTimeInSec(3);
        // to set it scrollable automatically
        // we use below method.
        sliderView.setAutoCycle(true);
        // to start autocycle below method is used.
        sliderView.startAutoCycle();
        //Image Slider End


        // GridView in Home Start
        gridView = (GridView) root.findViewById(R.id.gridView);
        databaseHelper = new DatabaseHelper(getContext());
        List<GetProductData> pList = new ArrayList<>();
        pList = databaseHelper.getAllProduct();
        GridViewAdapter adapter1 = new GridViewAdapter(getActivity(), R.layout.item_card, pList);
        gridView.setAdapter(adapter1);

//        Fragment newFragment;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                GetProductData currentRow = (GetProductData) parent.getItemAtPosition(position);
                String currentId = String.valueOf(currentRow.getId());

                product_details frg = new product_details();
                transaction.replace(R.id.navHostFragment, frg);
                Bundle bundle = new Bundle();
                bundle.putString("CPid", currentId);
                frg.setArguments(bundle);
                transaction.addToBackStack(null);
                transaction.commit();

//                Toast.makeText(getContext(), "You Clicked " + currentId, Toast.LENGTH_SHORT).show();
            }
        });
        // GridView in Home end
        return root;
    }

    void init(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        sliderView = view.findViewById(R.id.slider);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_menu, menu);
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
        if (id == R.id.action_search) {
            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.fade_out);
            transaction.replace(R.id.navHostFragment, new Search());
            transaction.addToBackStack(null);
            transaction.commit();
        }
        return super.onOptionsItemSelected(item);
    }
}