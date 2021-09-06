package com.pdstudios.shoppinglistfornetguru.screens.item_list

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
import com.pdstudios.shoppinglistfornetguru.database.item.ItemForm
import com.pdstudios.shoppinglistfornetguru.databinding.FragmentItemsShoppingListBinding

class ItemsShoppingList : Fragment(), ItemsRecyclerAdapter.AdapterListener {

    private lateinit var binding: FragmentItemsShoppingListBinding
    private lateinit var viewModel: ItemsViewModel
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: RecyclerView.Adapter<ItemsRecyclerAdapter.ViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //binging
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_items_shopping_list, container, false)

        //args
        var args = ItemsShoppingListArgs.fromBundle(requireArguments())

        //database
        val application = requireNotNull(this.activity).application
        val database = ShoppingDatabase.getInstance(application)

        //viewModel
        val factory = ItemsViewModelFactory(database, application, args.listID)
        viewModel = ViewModelProvider(this, factory).get(ItemsViewModel::class.java)
        binding.itemsViewModel = viewModel
        binding.lifecycleOwner = this

        //recyclerView
        val shoppingList = viewModel.shoppingLists
        layoutManager = LinearLayoutManager(this.context)
        binding.recyclerViewItems.layoutManager = layoutManager
        adapter = ItemsRecyclerAdapter(viewModel.itemList, shoppingList, this)
        binding.recyclerViewItems.adapter = adapter

        viewModel.shoppingLists.observe(viewLifecycleOwner) { list ->
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

                ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.recyclerViewItems)
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