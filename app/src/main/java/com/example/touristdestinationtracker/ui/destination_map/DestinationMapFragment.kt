package com.example.touristdestinationtracker.ui.destination_map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.touristdestinationtracker.databinding.FragmentDestinationMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DestinationMapFragment: Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: FragmentDestinationMapBinding

    override fun onCreateView(inflater: LayoutInflater,
                          container: ViewGroup?,
                          savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentDestinationMapBinding.inflate(layoutInflater)

        val supportMapFragment =
            childFragmentManager.findFragmentById(com.example.touristdestinationtracker.R.id.maps) as SupportMapFragment?

        supportMapFragment?.getMapAsync(this)

        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        var doubleLat = arguments?.get("latitude").toString()
        var doubleLong = arguments?.get("longitude").toString()
        mMap = googleMap

        val location = LatLng(doubleLat.toDouble(), doubleLong.toDouble())
        mMap.addMarker(
            MarkerOptions()
            .position(location)
            .title("Your destination location"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
    }
}
