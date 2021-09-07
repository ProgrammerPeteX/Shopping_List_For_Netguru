package com.pdstudios.shoppinglistfornetguru.screens.current_shopping_lists

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pdstudios.shoppinglistfornetguru.SwipeGesture
import com.pdstudios.shoppinglistfornetguru.SharedViewModel
import com.pdstudios.shoppinglistfornetguru.R
import com.pdstudios.shoppinglistfornetguru.database.ShoppingDatabase
import com.pdstudios.shoppinglistfornetguru.database.shopping_list.ShoppingListsForm
import com.pdstudios.shoppinglistfornetguru.databinding.FragmentCurrentShoppingListBinding

class CurrentShoppingList : Fragment(), CurrentRecyclerAdapter.AdapterListener {

    private lateinit var binding: FragmentCurrentShoppingListBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var viewModel: CurrentViewModel

    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: RecyclerView.Adapter<CurrentRecyclerAdapter.ViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        //setTitle
        sharedViewModel.actionBarTitle.value = "Shopping List - Current"

        //binding
        binding = DataBindingUtil.inflate(
            inflater,R.layout.fragment_current_shopping_list,container,false)

        //database
        val application = requireNotNull(this.activity).application
        val shoppingListsDao = ShoppingDatabase.getInstance(application).shoppingListsDao

        //viewModel
        val factory = CurrentViewModelFactory(shoppingListsDao, application )
        viewModel = ViewModelProvider(this, factory).get(CurrentViewModel::class.java)
        binding.currentViewModel = viewModel
        binding.lifecycleOwner = this

        //menu
        setHasOptionsMenu(true)

        //recyclerView
        layoutManager = LinearLayoutManager(this.context)
        binding.recyclerViewCurrent.layoutManager = layoutManager
        adapter = CurrentRecyclerAdapter(viewModel.shoppingLists, this)
        binding.recyclerViewCurrent.adapter = adapter

        viewModel.shoppingLists.observe(viewLifecycleOwner) {
            adapter.notifyDataSetChanged()
        }

        val swipeLeftIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_delete_24)!!
        val swipeRightIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_archive_24)!!
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
                        shoppingList.isArchived = true
                        updateShoppingLists(shoppingList)
                        adapter.notifyDataSetChanged()
                    }
                    else -> {}
                }
            }
        }

        ItemTouchHelper(swipeGesture).attachToRecyclerView(binding.recyclerViewCurrent)

        return binding.root
    }

    override fun updateShoppingLists(shoppingLists: ShoppingListsForm) {
        viewModel.updateShoppingLists(shoppingLists)
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