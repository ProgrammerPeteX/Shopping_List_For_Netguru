package com.pdstudios.shoppinglistfornetguru.screens.current_shopping_lists

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pdstudios.shoppinglistfornetguru.R
import com.pdstudios.shoppinglistfornetguru.databinding.FragmentCurrentShoppingListBinding

class CurrentShoppingList : Fragment() {

    private lateinit var binding: FragmentCurrentShoppingListBinding
    private lateinit var viewModel: CurrentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //binding
        binding = DataBindingUtil.inflate(
            inflater,R.layout.fragment_current_shopping_list,container,false)

        //viewModel
        viewModel = ViewModelProvider(this).get(CurrentViewModel::class.java)
        binding.currentViewModel = viewModel
        binding.lifecycleOwner = this

        //menu
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.archived_shopping_lists, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuItem_archived -> navigateToArchived()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navigateToArchived() {
        this.findNavController().navigate(CurrentShoppingListDirections
            .actionCurrentShoppingListToArchivedShoppingList())
    }

}