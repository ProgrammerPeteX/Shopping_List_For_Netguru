package com.pdstudios.shoppinglistfornetguru.screens.shopping_list_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.pdstudios.shoppinglistfornetguru.R
import com.pdstudios.shoppinglistfornetguru.databinding.FragmentShoppingListDetailsBinding

class ShoppingListDetails : Fragment() {

    private lateinit var binding: FragmentShoppingListDetailsBinding
    private lateinit var viewModel: DetailsViewModel

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

        return binding.root
    }

}