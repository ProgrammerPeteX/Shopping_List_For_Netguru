package com.pdstudios.shoppinglistfornetguru.screens.archived_shopping_lists

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pdstudios.shoppinglistfornetguru.R
import com.pdstudios.shoppinglistfornetguru.SharedViewModel
import com.pdstudios.shoppinglistfornetguru.SwipeGesture
import com.pdstudios.shoppinglistfornetguru.database.ShoppingDatabase
import com.pdstudios.shoppinglistfornetguru.databinding.FragmentArchivedShoppingListBinding


class ArchivedShoppingList : Fragment() {

    private lateinit var binding: FragmentArchivedShoppingListBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var viewModel: ArchivedViewModel

    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: RecyclerView.Adapter<ArchivedRecyclerAdapter.ViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //setTitle
        sharedViewModel.actionBarTitle.value = "Shopping List - Archived"

        //binding
        binding = DataBindingUtil.inflate(
            inflater,R.layout.fragment_archived_shopping_list, container, false)

        //database
        val application = requireNotNull(this.activity).application
        val shoppingListDao = ShoppingDatabase.getInstance(application).shoppingListsDao

        //viewModel
        val factory = ArchivedViewModelFactory(shoppingListDao, application)
        viewModel = ViewModelProvider(this, factory).get(ArchivedViewModel::class.java)
        binding.archivedViewModel = viewModel
        binding.lifecycleOwner = this

        //menu
        setHasOptionsMenu(true)

        //recyclerView
        layoutManager = LinearLayoutManager(this.context)
        binding.recyclerViewArchived.layoutManager = layoutManager
        adapter = ArchivedRecyclerAdapter(viewModel.shoppingLists)
        binding.recyclerViewArchived.adapter = adapter

        viewModel.shoppingLists.observe(viewLifecycleOwner) {
            adapter.notifyDataSetChanged()
        }
        val swipeLeftIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_delete_24)!!
        val swipeRightIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_unarchive_24)!!
        val swipeDir = LEFT or RIGHT
        val swipeGesture = object : SwipeGesture(swipeLeftIcon, swipeRightIcon, swipeDir) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val shoppingList = viewModel.shoppingLists.value!![viewHolder.adapterPosition]
                when(direction) {
                    LEFT -> {
                        viewModel.deleteFromShoppingLists(shoppingList.listID)
                        adapter.notifyDataSetChanged()
                    }
                    RIGHT -> {
                        shoppingList.isArchived = false
                        viewModel.updateShoppingLists(shoppingList)
                        adapter.notifyDataSetChanged()
                    }
                    else -> {}
                }
            }
        }

        ItemTouchHelper(swipeGesture).attachToRecyclerView(binding.recyclerViewArchived)

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