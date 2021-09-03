package com.pdstudios.shoppinglistfornetguru.screens.shopping_list_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pdstudios.shoppinglistfornetguru.R
import com.pdstudios.shoppinglistfornetguru.databinding.FragmentShoppingListDetailsBinding

class ShoppingListDetails : Fragment() {

    private lateinit var binding: FragmentShoppingListDetailsBinding
    private lateinit var viewModel: DetailsViewModel
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: RecyclerView.Adapter<DetailsRecyclerAdapter.ViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //binging
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_shopping_list_details, container, false)

        //viewModel
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        binding.detailsViewModel = viewModel
        binding.lifecycleOwner = this

        //recyclerView
        layoutManager = LinearLayoutManager(this.context)
        binding.recyclerViewDetails.layoutManager = layoutManager
        adapter = DetailsRecyclerAdapter()
        binding.recyclerViewDetails.adapter = adapter



        return binding.root
    }

}