package com.pdstudios.shoppinglistfornetguru.screens.item_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pdstudios.shoppinglistfornetguru.R
import com.pdstudios.shoppinglistfornetguru.SharedViewModel
import com.pdstudios.shoppinglistfornetguru.SwipeGesture
import com.pdstudios.shoppinglistfornetguru.database.ShoppingDatabase
import com.pdstudios.shoppinglistfornetguru.database.item.ItemForm
import com.pdstudios.shoppinglistfornetguru.databinding.FragmentItemsShoppingListBinding

class ItemsShoppingList : Fragment(), ItemsRecyclerAdapter.AdapterListener {

    private lateinit var binding: FragmentItemsShoppingListBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var viewModel: ItemsViewModel

    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: RecyclerView.Adapter<ItemsRecyclerAdapter.ViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //args
        val args = ItemsShoppingListArgs.fromBundle(requireArguments())

        //setTitle
        sharedViewModel.actionBarTitle.value = args.listName

        //binging
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_items_shopping_list, container, false)

        //database
        val application = requireNotNull(this.activity).application
        val database = ShoppingDatabase.getInstance(application)

        //viewModel
        val factory = ItemsViewModelFactory(database, application, args.listID)
        viewModel = ViewModelProvider(this, factory).get(ItemsViewModel::class.java)
        binding.itemsViewModel = viewModel
        binding.lifecycleOwner = this

        //recyclerView
        val shoppingList = viewModel.shoppingList
        layoutManager = LinearLayoutManager(this.context)
        binding.recyclerViewItems.layoutManager = layoutManager
        adapter = ItemsRecyclerAdapter(viewModel.itemList, shoppingList, this)
        binding.recyclerViewItems.adapter = adapter

        viewModel.shoppingList.observe(viewLifecycleOwner) { list ->
            adapter.notifyDataSetChanged()
            if (!list.isArchived) {
                val swipeLeftIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_delete_24)!!
                val swipeRightIcon = null
                val swipeDir = LEFT
                val swipeGesture = object : SwipeGesture(swipeLeftIcon, swipeRightIcon, swipeDir) {
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val item = viewModel.itemList.value!![viewHolder.adapterPosition]
                        when(direction) {
                            LEFT -> {
                                viewModel.delete(item.itemID)
                                adapter.notifyDataSetChanged()
                            }
                            RIGHT -> {}
                            else -> {}
                        }
                    }
                }

                ItemTouchHelper(swipeGesture).attachToRecyclerView(binding.recyclerViewItems)
            } else {
                binding.buttonAddItem.visibility = View.GONE
            }
        }

        viewModel.itemList.observe(viewLifecycleOwner) {
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

    override fun updateItemList(item: ItemForm) {
        viewModel.updateItemList(item)
    }

}