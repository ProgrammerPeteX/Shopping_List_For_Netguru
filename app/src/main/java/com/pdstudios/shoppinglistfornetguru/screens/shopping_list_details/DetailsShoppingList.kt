package com.pdstudios.shoppinglistfornetguru.screens.shopping_list_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pdstudios.shoppinglistfornetguru.R
import com.pdstudios.shoppinglistfornetguru.databinding.FragmentDetailsShoppingListBinding

class DetailsShoppingList : Fragment() {

    private lateinit var binding: FragmentDetailsShoppingListBinding
    private lateinit var viewModel: DetailsViewModel
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: RecyclerView.Adapter<DetailsRecyclerAdapter.ViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //binging
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_details_shopping_list, container, false)

        //viewModel
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        binding.detailsViewModel = viewModel
        binding.lifecycleOwner = this

        //recyclerView
        layoutManager = LinearLayoutManager(this.context)
        binding.recyclerViewDetails.layoutManager = layoutManager
        adapter = DetailsRecyclerAdapter(viewModel.itemList)
        binding.recyclerViewDetails.adapter = adapter

        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {

                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    when (direction) {
                        ItemTouchHelper.LEFT -> {//DELETE
                            viewModel.itemList.value?.removeAt(viewHolder.adapterPosition)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
            }

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.recyclerViewDetails)

        //observers
        viewModel.notifyAdapter.observe(viewLifecycleOwner) {
            it?.let {
                adapter.notifyDataSetChanged()
            }
        }

        return binding.root
    }

}