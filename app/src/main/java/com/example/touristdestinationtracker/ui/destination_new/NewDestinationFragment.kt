package com.example.touristdestinationtracker.ui.destination_new

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.touristdestinationtracker.R
import com.example.touristdestinationtracker.data.room.DestinationConverters
import com.example.touristdestinationtracker.databinding.FragmentNewDestinationBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class NewDestinationFragment : Fragment() {

    private val dateDisplayFormat = SimpleDateFormat("dd.MM.yyyy")
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val permissionId = 1

    private lateinit var binding: FragmentNewDestinationBinding
    private val viewModel: DestinationNewViewModel by viewModel()

    private var longitude = ""
    private var latitude = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        binding = FragmentNewDestinationBinding.inflate(layoutInflater)
        binding.bTakeImage.setOnClickListener { takeImage() }
        binding.bSaveDestination.setOnClickListener { saveDestination() }
        binding.bGetLocation.setOnClickListener { getLocation() }

        requestPermissions()

        return binding.root
    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (!isPermissionGranted()) {
            requestPermissions()
            return
        }
        if (!isLocationEnabled()) {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
            return
        }
        mFusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
            val location: Location? = task.result
            if (location != null) {
                latitude = location.latitude.toString()
                longitude = location.longitude.toString()
                try {
                    val geocoder = Geocoder(requireContext(), Locale.getDefault())
                    val list: List<Address> =
                        geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    binding.apply {
                        tvDestinationLocationCountry.text = list[0].countryName
                        tvDestinationLocationCity.text = list[0].locality
                    }
                } catch (e: Exception) {
                    Log.e("getLocation():", e.toString())
                }
            } else Toast.makeText(context, getString(R.string.location_cannot_be_accessed), Toast.LENGTH_SHORT).show()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        requestPermissions(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode != permissionId) return
        if ((grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED)) return
        if (!isLocationEnabled()) {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
    }

    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK ) {
            val takenImage = result.data?.extras?.get("data") as Bitmap
            binding.ivDestinationImage.setImageBitmap(takenImage)
        }
    }

    private fun takeImage() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (activity?.let { intent.resolveActivity(it.packageManager) } != null) {
            resultLauncher.launch(intent)
        }
        binding.tvDestinationDate.text = dateDisplayFormat.format(Date())
    }

    private fun saveDestination() {
        try {
            val description = binding.etDestinationDescriptionInput.text.toString()
            val date = binding.tvDestinationDate.text.toString()
            val city = binding.tvDestinationLocationCity.text.toString()
            val country = binding.tvDestinationLocationCountry.text.toString()
            val image = binding.ivDestinationImage.drawable.toBitmap()
            val converter = DestinationConverters()

            viewModel.save(
                description,
                date,
                city,
                country,
                converter.convertImageToByteArray(image),
                latitude,
                longitude
            )
            Toast.makeText(context, getString(R.string.message_saving), Toast.LENGTH_SHORT).show()

            val action =
                NewDestinationFragmentDirections.actionNewDestinationFragmentToDestinationListFragment()
            findNavController().navigate(action)
        } catch (e: Exception) {
            Toast.makeText(context, getString(R.string.picture_warning), Toast.LENGTH_SHORT).show()
        }
    }
}