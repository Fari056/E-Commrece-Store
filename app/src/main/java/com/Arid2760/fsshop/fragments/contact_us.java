package com.Arid2760.fsshop.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contact_us, container, false);
        mapView = v.findViewById(R.id.map);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
        return v;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        google_Map = googleMap;
        LatLng lahoreArfa = new LatLng(31.4758, 74.3426);
        MapsInitializer.initialize(getContext());
        google_Map.addMarker(new MarkerOptions().position(lahoreArfa).title("Arfa Software Tower"));
        google_Map.moveCamera(CameraUpdateFactory.newLatLng(lahoreArfa));
    }
}
