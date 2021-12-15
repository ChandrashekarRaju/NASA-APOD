package com.nasa.apod.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nasa.apod.databinding.FragmentFavApodBinding
import com.nasa.apod.ui.adapter.APODListAdapter
import com.nasa.apod.ui.viewmodel.FavoriteAPODViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesAPODFragment: Fragment() {

    private lateinit var binding: FragmentFavApodBinding
    private val viewModel: FavoriteAPODViewModel by viewModels()
    @Inject
    lateinit var apodListAdapter: APODListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavApodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        viewFavoriteAPODList()
    }

    private fun initRecyclerView() {
        binding.apply {
            recyclerViewFavApodList.adapter = apodListAdapter
            recyclerViewFavApodList.layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun viewFavoriteAPODList() {
        viewModel.favoriteAPOD().observe(viewLifecycleOwner) { result ->
            apodListAdapter.differ.submitList(result)
        }
    }
}