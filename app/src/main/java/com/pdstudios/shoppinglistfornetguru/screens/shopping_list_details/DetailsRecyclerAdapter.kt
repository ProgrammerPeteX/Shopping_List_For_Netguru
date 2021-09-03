package com.pdstudios.shoppinglistfornetguru.screens.shopping_list_details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pdstudios.shoppinglistfornetguru.databinding.ShoppingListCardBinding


class DetailsRecyclerAdapter:
    RecyclerView.Adapter<DetailsRecyclerAdapter.ViewHolder>() {

    var isArchived = false
    var list = mutableListOf(1,2,3,4,5,6,7,8,9,112,233,434,654,457,8786,6786,678,867867,44,5,64,46,2)
    private lateinit var binding: ShoppingListCardBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ShoppingListCardBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.shoppingListName.setText("Item ${list[position]}")
        holder.cardView.setOnClickListener { view ->
//            navigateBack(view, isArchived)
        }
    }

//    private fun navigateBack(view: View, isArchived: Boolean) {
//        val destination = when (isArchived) {
//            true -> ShoppingListDetailsDirections.actionShoppingListDetailsToArchivedShoppingList()
//            false -> ShoppingListDetailsDirections.actionShoppingListDetailsToCurrentShoppingList()
//        }
//        view.findNavController().navigate(destination)
//    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var shoppingListName = binding.editTextShoppingListName
        var cardView = binding.cardView
    }
}