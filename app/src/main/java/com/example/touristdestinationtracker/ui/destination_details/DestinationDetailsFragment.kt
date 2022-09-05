package com.example.touristdestinationtracker.ui.destination_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.touristdestinationtracker.R
import com.example.touristdestinationtracker.data.room.DestinationConverters
import com.example.touristdestinationtracker.databinding.FragmentDestinationDetailsBinding
import com.example.touristdestinationtracker.model.Destination
import org.koin.androidx.viewmodel.ext.android.viewModel

class DestinationDetailsFragment : Fragment() {

    private lateinit var binding: FragmentDestinationDetailsBinding
    private val viewModel:DestinationDetailsViewModel by viewModel()
    private val args: DestinationDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDestinationDetailsBinding.inflate(layoutInflater)
        binding.bShowOnMap.setOnClickListener { showLocationOnMap() }

        return binding.root
    }

    private fun showLocationOnMap() {
        val destination = viewModel.getDestinationById(args.destinationId)
        val action = destination?.let {
            DestinationDetailsFragmentDirections
                .actionDestinationDetailsFragmentToDestinationMapFragment(
                    it.latitude,
                    it.longitude
                )
        }
        if (action != null) {
            findNavController().navigate(action)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val destination = viewModel.getDestinationById(args.destinationId)
        display(destination)
    }

    private fun display(destination: Destination?) {
        val converter =  DestinationConverters()
        destination?.let {
            binding.apply {
                tvDestinationLocation.text = destination.locationCity + ", " + destination.locationCountry
                tvDestinationDate.text = destination.dateAdded
                tvDestinationDescritpion.text = "Description: " + destination.shortDescription
                ivDestinationDetailsImage.setImageBitmap(converter.convertByteArrayToImage(destination.image))
                return
            }
        }
        binding.tvDestinationLocation.text = getString(R.string.label_no_such_destination)
    }
}