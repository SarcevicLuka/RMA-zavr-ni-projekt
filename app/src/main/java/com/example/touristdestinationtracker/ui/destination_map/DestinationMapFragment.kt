package com.example.touristdestinationtracker.ui.destination_map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.touristdestinationtracker.R
import com.example.touristdestinationtracker.databinding.FragmentDestinationMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DestinationMapFragment: Fragment(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private lateinit var binding: FragmentDestinationMapBinding

    override fun onCreateView(inflater: LayoutInflater,
                          container: ViewGroup?,
                          savedInstanceState: Bundle?): View {
        super.onCreate(savedInstanceState)
        binding = FragmentDestinationMapBinding.inflate(layoutInflater)

        val supportMapFragment =
            childFragmentManager.findFragmentById(binding.maps.id) as SupportMapFragment?

        supportMapFragment?.getMapAsync(this)

        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val doubleLat = arguments?.get("latitude").toString()
        val doubleLong = arguments?.get("longitude").toString()
        val zoomLevel = 10f
        map = googleMap

        val location = LatLng(doubleLat.toDouble(), doubleLong.toDouble())
        map.addMarker(
            MarkerOptions()
            .position(location)
            .title(getString(R.string.map_destination_title)))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel))
        map.moveCamera(CameraUpdateFactory.newLatLng(location))
    }
}
