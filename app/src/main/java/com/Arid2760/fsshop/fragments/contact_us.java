package com.Arid2760.fsshop.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.Arid2760.fsshop.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class contact_us extends Fragment implements OnMapReadyCallback {
    MapView mapView;
    GoogleMap google_Map;
    ImageButton backBtn;
    TextView callMe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contact_us, container, false);
        init(v);
        mapView = v.findViewById(R.id.map);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
            try {
                MapsInitializer.initialize(getContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager().getBackStackEntryCount() != 0) {
                    getFragmentManager().popBackStack();
                }
            }
        });
        callMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = "+923337774206";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });


        return v;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        google_Map = googleMap;
        LatLng lahoreArfa = new LatLng(31.4758, 74.3426);
        google_Map.addMarker(new MarkerOptions().position(lahoreArfa).title("Arfa Software Tower"));
        google_Map.moveCamera(CameraUpdateFactory.newLatLng(lahoreArfa));
    }
    void  init(View v){
        backBtn = v.findViewById(R.id.backBtn1);
        callMe = v.findViewById(R.id.callMeNow);
    }
}
