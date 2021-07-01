package com.Arid2760.fsshop.fragments;

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
import androidx.fragment.app.FragmentTransaction;

import com.Arid2760.fsshop.DatabaseHelper;
import com.Arid2760.fsshop.adapters.GridViewAdapter;
import com.Arid2760.fsshop.R;
import com.Arid2760.fsshop.adapters.ListViewAdapter;
import com.Arid2760.fsshop.adapters.SliderAdapter;
import com.Arid2760.fsshop.adminPenal.manage_Products;
import com.Arid2760.fsshop.gertterSetter.GetProductData;
import com.Arid2760.fsshop.gertterSetter.SliderData;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Home extends Fragment {
    ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
    GridView gridView;
    Toolbar toolbar;
    DatabaseHelper databaseHelper;
    SliderView sliderView;
    String url;
    private static final String allPUrl = "http://192.168.8.107/FSElect/getAllProducts.php";
    GetProductData data;


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
        /*databaseHelper = new DatabaseHelper(getContext());
        pList = databaseHelper.getAllProduct();
        List<GetProductData> pList = new ArrayList<>();
        GridViewAdapter adapter1 = new GridViewAdapter(getActivity(), R.layout.item_card, pList);
        gridView.setAdapter(adapter1);*/


        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, allPUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
//                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                    if (response.equals("Failed")) {
                        Toast.makeText(getContext(), "Credentials are not valid ", Toast.LENGTH_SHORT).show();
                    } else {
                        List<GetProductData> pList = new ArrayList<>();
                        try {
                            JSONArray res = new JSONArray(response);
                            for (int i = 0; i < res.length(); i++) {
                                JSONObject result = res.getJSONObject(i);
                                String id = result.getString("id");
                                String name = result.getString("name");
                                String price = result.getString("price");
                                String description = result.getString("description");
                                String image = result.getString("image");
                                url = "http://192.168.8.107/FSElect/images/" + image;


                                data = new GetProductData();
                                data.setId(Integer.parseInt(id));
                                data.setName(name);
                                data.setPrice(price);
                                data.setDescription(description);
                                data.setImageBitmap(url);

                                pList.add(data);
                            }
                            if (pList.size() != 0) {
                                GridViewAdapter adapter1 = new GridViewAdapter(getActivity(), R.layout.item_card, pList);
                                gridView.setAdapter(adapter1);
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

//        Fragment newFragment;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                GetProductData currentRow = (GetProductData) parent.getItemAtPosition(position);
                String currentId = String.valueOf(currentRow.getId());
//                Toast.makeText(getContext(), currentId, Toast.LENGTH_SHORT).show();

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

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (id == R.id.action_search) {
            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.fade_out);
            transaction.replace(R.id.navHostFragment, new Search());
            transaction.addToBackStack(null);
            transaction.commit();
        }
        return super.onOptionsItemSelected(item);
    }
}