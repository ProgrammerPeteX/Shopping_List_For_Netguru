package com.pdstudios.shoppinglistfornetguru.screens.archived_shopping_lists

import android.text.InputType
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pdstudios.shoppinglistfornetguru.database.shopping_list.ShoppingListsForm
import com.pdstudios.shoppinglistfornetguru.databinding.ShoppingListCardBinding
import com.pdstudios.shoppinglistfornetguru.screens.current_shopping_lists.CurrentShoppingListDirections

class ArchivedRecyclerAdapter(
    private var list: LiveData<List<ShoppingListsForm>>
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
        holder.shoppingListName.text = list.value!![position].name

        holder.cardView.setOnClickListener { view ->
            if (!isLongClick) navigateToDetails(view)
        }
    }

    override fun getItemCount(): Int {
        return list.value?.size ?: 0
    }

    inner class ViewHolder(itemView: View):
        RecyclerView.ViewHolder(itemView) {
        var shoppingListName = binding.textViewShoppingListName
        var cardView = binding.cardView
    }

    private fun navigateToDetails(view: View) {
        view.findNavController().navigate(
            ArchivedShoppingListDirections
            .actionArchivedShoppingListToShoppingListDetails())
    }
}

