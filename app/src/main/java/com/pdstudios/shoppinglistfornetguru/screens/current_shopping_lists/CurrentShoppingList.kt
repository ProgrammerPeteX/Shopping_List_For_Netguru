package com.pdstudios.shoppinglistfornetguru.screens.current_shopping_lists

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pdstudios.shoppinglistfornetguru.R
import com.pdstudios.shoppinglistfornetguru.databinding.FragmentCurrentShoppingListBinding

class CurrentShoppingList : Fragment() {

    private lateinit var binding: FragmentCurrentShoppingListBinding
    private lateinit var viewModel: CurrentViewModel

    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: RecyclerView.Adapter<CurrentRecyclerAdapter.ViewHolder>

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

        //recyclerView
        layoutManager = LinearLayoutManager(this.context)
        binding.recyclerViewCurrent.layoutManager = layoutManager
        adapter = CurrentRecyclerAdapter(viewModel.shoppingLists)
        binding.recyclerViewCurrent.adapter = adapter

        //observers
        viewModel.notifyAdapater.observe(viewLifecycleOwner) {
            it?.let {
                adapter.notifyDataSetChanged()
            }
        }
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