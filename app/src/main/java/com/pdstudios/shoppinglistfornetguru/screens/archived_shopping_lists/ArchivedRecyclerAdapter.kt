package com.pdstudios.shoppinglistfornetguru.screens.archived_shopping_lists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pdstudios.shoppinglistfornetguru.databinding.ShoppingListCardBinding

class ArchivedRecyclerAdapter: RecyclerView.Adapter<ArchivedRecyclerAdapter.ViewHolder>() {

    private lateinit var binding: ShoppingListCardBinding
    var list = mutableListOf(1,2,3,4,5,6,7,8,9,112)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ShoppingListCardBinding.inflate(inflater,parent,false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.shoppingListName.text = "Archived List ${list[position]}"
        holder.cardView.setOnClickListener { view ->
            navigateToDetails(view)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var shoppingListName = binding.textViewShoppingListName
        var cardView = binding.cardView
    }

    private fun navigateToDetails(view: View) {
        view.findNavController().navigate(
            ArchivedShoppingListDirections
            .actionArchivedShoppingListToShoppingListDetails())
    }

}

