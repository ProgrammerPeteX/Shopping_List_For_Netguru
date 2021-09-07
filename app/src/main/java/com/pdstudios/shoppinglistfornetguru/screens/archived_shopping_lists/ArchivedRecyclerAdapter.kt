package com.pdstudios.shoppinglistfornetguru.screens.archived_shopping_lists

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pdstudios.shoppinglistfornetguru.database.shopping_list.ShoppingListsForm
import com.pdstudios.shoppinglistfornetguru.databinding.ShoppingListCardBinding

class ArchivedRecyclerAdapter(
    private var lists: LiveData<List<ShoppingListsForm>>
): RecyclerView.Adapter<ArchivedRecyclerAdapter.ViewHolder>() {

    private lateinit var binding: ShoppingListCardBinding
    private var isLongClick = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ShoppingListCardBinding.inflate(inflater,parent,false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shoppingList = lists.value!![position]
        holder.shoppingListName.text = shoppingList.name

        holder.cardView.setOnClickListener { view ->
            if (!isLongClick) navigateToItems(view, shoppingList.listID, shoppingList.name)
        }
    }

    override fun getItemCount(): Int {
        return lists.value?.size ?: 0
    }

    inner class ViewHolder(itemView: View):
        RecyclerView.ViewHolder(itemView) {
        var shoppingListName = binding.textViewShoppingListName
        var cardView = binding.cardView
    }

    private fun navigateToItems(view: View, listID: Long, listName: String) {
        view.findNavController().navigate(
            ArchivedShoppingListDirections
            .actionArchivedShoppingListToShoppingListItems(listID, listName))
    }
}

