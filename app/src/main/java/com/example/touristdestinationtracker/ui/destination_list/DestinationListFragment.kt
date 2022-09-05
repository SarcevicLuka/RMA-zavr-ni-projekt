package com.example.touristdestinationtracker.ui.destination_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.touristdestinationtracker.R
import com.example.touristdestinationtracker.databinding.FragmentDestinationListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class DestinationListFragment : Fragment(), OnDestinationEventListener {

    private lateinit var binding: FragmentDestinationListBinding
    private lateinit var adapter: DestinationAdapter
    private val viewModel: DestinationListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDestinationListBinding.inflate(layoutInflater)
        binding.fabAddDestination.setOnClickListener { showCreateNewDestinationFragment() }

        setupRecyclerView()
        viewModel.destinations.observe(viewLifecycleOwner) {
            if (it != null && it.isNotEmpty()) {
                adapter.setDestinations(it.reversed())
            }
        }

        ItemTouchHelper(
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: ViewHolder, target: ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                    viewModel.delete(adapter.getDestinationAt(viewHolder.adapterPosition))
                    Toast.makeText(activity, getString(R.string.message_deleted), Toast.LENGTH_SHORT).show()
                }
            }).attachToRecyclerView(binding.destinationListRv)

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.destinationListRv.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false,
        )
        adapter = DestinationAdapter()
        adapter.onDestinationSelectedListener = this
        binding.destinationListRv.adapter = adapter
    }

    override fun onDestinationSelected(id: Long?) {
        val action = DestinationListFragmentDirections
            .actionDestinationListFragmentToDestinationDetailsFragment(id ?: -1)
        findNavController().navigate(action)
    }

    private fun showCreateNewDestinationFragment() {
        val action = DestinationListFragmentDirections
            .actionDestinationListFragmentToNewDestinationFragment()
        findNavController().navigate(action)
    }
}