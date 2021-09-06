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
import com.pdstudios.shoppinglistfornetguru.database.ShoppingDatabase
import com.pdstudios.shoppinglistfornetguru.database.details.DetailsForm
import com.pdstudios.shoppinglistfornetguru.databinding.FragmentDetailsShoppingListBinding

class DetailsShoppingList : Fragment(), DetailsRecyclerAdapter.AdapterListener {

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

        //args
        var args = DetailsShoppingListArgs.fromBundle(requireArguments())

        //database
        val application = requireNotNull(this.activity).application
        val database = ShoppingDatabase.getInstance(application)

        //viewModel
        val factory = DetailsViewModelFactory(database, application, args.listID)
        viewModel = ViewModelProvider(this, factory).get(DetailsViewModel::class.java)
        binding.detailsViewModel = viewModel
        binding.lifecycleOwner = this

        //recyclerView
        val shoppingList = viewModel.shoppingList
        layoutManager = LinearLayoutManager(this.context)
        binding.recyclerViewDetails.layoutManager = layoutManager
        adapter = DetailsRecyclerAdapter(viewModel.itemList, shoppingList, this)
        binding.recyclerViewDetails.adapter = adapter

        viewModel.shoppingList.observe(viewLifecycleOwner) { list ->
            adapter.notifyDataSetChanged()
            if (!list.isArchived) {
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
                                    val itemID =
                                        viewModel.itemList.value!![viewHolder.adapterPosition].itemID
                                    viewModel.delete(itemID)
                                    adapter.notifyDataSetChanged()
                                }
                            }
                        }
                    }

                ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.recyclerViewDetails)
            } else {
                binding.buttonAddItem.visibility = View.GONE
            }
        }

        viewModel.itemList.observe(viewLifecycleOwner) {
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

    override fun updateItemList(item: DetailsForm) {
        viewModel.updateItemList(item)
    }

}