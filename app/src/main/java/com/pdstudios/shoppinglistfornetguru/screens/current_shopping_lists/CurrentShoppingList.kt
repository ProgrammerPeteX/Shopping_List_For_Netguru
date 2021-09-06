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
import com.pdstudios.shoppinglistfornetguru.database.shopping_list.ShoppingListsForm
import com.pdstudios.shoppinglistfornetguru.databinding.FragmentCurrentShoppingListBinding

class CurrentShoppingList : Fragment(), CurrentRecyclerAdapter.AdapterListener {

    private lateinit var binding: FragmentCurrentShoppingListBinding
    private lateinit var viewModel: CurrentViewModel

    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: RecyclerView.Adapter<CurrentRecyclerAdapter.ViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
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
//            Log.i("test", "hi, this is the  shoppingLists Observer :)")
        }

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
                    val shoppingList = viewModel.shoppingLists.value!![viewHolder.adapterPosition]
                    when (direction) {
                        LEFT -> {//DELETE
//                            viewModel.shoppingLists.value?.removeAt(viewHolder.adapterPosition)
                            val listID = shoppingList.listID
                            viewModel.deleteFromShoppingLists(listID)
                            adapter.notifyDataSetChanged()
                        }
                        RIGHT -> {//ARCHIVED
                            shoppingList.isArchived = true
                            updateShoppingList(shoppingList)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
            }

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.recyclerViewCurrent)

        return binding.root
    }

    override fun updateShoppingList(shoppingLists: ShoppingListsForm) {
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