package com.pdstudios.shoppinglistfornetguru.screens.current_shopping_lists

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pdstudios.shoppinglistfornetguru.R
import com.pdstudios.shoppinglistfornetguru.database.ShoppingDatabase
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

        //database
        val application = requireNotNull(this.activity).application
        val shoppingDatabase = ShoppingDatabase.getInstance(application)

        //viewModel
        val factory = CurrentViewModelFactory(shoppingDatabase, application)
        viewModel = ViewModelProvider(this, factory).get(CurrentViewModel::class.java)
        binding.currentViewModel = viewModel
        binding.lifecycleOwner = this


        //menu
        setHasOptionsMenu(true)

        //recyclerView
        layoutManager = LinearLayoutManager(this.context)
        binding.recyclerViewCurrent.layoutManager = layoutManager
        adapter = CurrentRecyclerAdapter(viewModel.shoppingLists)
        binding.recyclerViewCurrent.adapter = adapter

        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, LEFT or RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {

                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    when (direction) {
                        LEFT -> {//DELETE
                            viewModel.shoppingLists.value?.removeAt(viewHolder.adapterPosition)
                            adapter.notifyDataSetChanged()
                        }
                        RIGHT -> {//ARCHIVED
                            adapter.notifyDataSetChanged()
                        }
                    }
                }

            }

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.recyclerViewCurrent)

        //observers
        viewModel.notifyAdapter.observe(viewLifecycleOwner) {
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