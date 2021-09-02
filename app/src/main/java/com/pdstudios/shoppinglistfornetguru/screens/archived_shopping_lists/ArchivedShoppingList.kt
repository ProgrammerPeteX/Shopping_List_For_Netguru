package com.pdstudios.shoppinglistfornetguru.screens.archived_shopping_lists

import android.os.Bundle
import android.view.*
import android.widget.Adapter
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pdstudios.shoppinglistfornetguru.R
import com.pdstudios.shoppinglistfornetguru.databinding.FragmentArchivedShoppingListBinding
import com.pdstudios.shoppinglistfornetguru.screens.current_shopping_lists.CurrentRecyclerAdapter


class ArchivedShoppingList : Fragment() {

    private lateinit var binding: FragmentArchivedShoppingListBinding
    private lateinit var viewModel: ArchivedViewModel
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: RecyclerView.Adapter<ArchivedRecyclerAdapter.ViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //binding
        binding = DataBindingUtil.inflate(
            inflater,R.layout.fragment_archived_shopping_list, container, false)

        //viewModel
        viewModel = ViewModelProvider(this).get(ArchivedViewModel::class.java)
        binding.archivedViewModel = viewModel
        binding.lifecycleOwner = this

        //menu
        setHasOptionsMenu(true)

        //recyclerView
        layoutManager = LinearLayoutManager(this.context)
        binding.recyclerViewArchived.layoutManager = layoutManager
        adapter = ArchivedRecyclerAdapter()
        binding.recyclerViewArchived.adapter = adapter

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.current_shopping_lists, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuItem_current -> navigateToCurrent()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navigateToCurrent() {
        this.findNavController().navigate(ArchivedShoppingListDirections
            .actionArchivedShoppingListToCurrentShoppingList())
    }
}