package com.pdstudios.shoppinglistfornetguru.screens.current_shopping_lists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pdstudios.shoppinglistfornetguru.databinding.ShoppingListCardBinding

class CurrentRecyclerAdapter(
    private var list: LiveData<MutableList<Int>>
):
    RecyclerView.Adapter<CurrentRecyclerAdapter.ViewHolder>(){

    private lateinit var binding: ShoppingListCardBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ShoppingListCardBinding.inflate(inflater,parent,false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.shoppingListName.text = "Shopping List ${list.value!![position]}"
        holder.cardView.setOnClickListener { view ->
            navigateToDetails(view)
        }
    }

    override fun getItemCount(): Int {
        return list.value!!.size
    }

    inner class ViewHolder(itemView: View):
        RecyclerView.ViewHolder(itemView) {
            var shoppingListName = binding.textViewShoppingListName
            var cardView = binding.cardView
        }

    private fun navigateToDetails(view: View) {
        view.findNavController().navigate(CurrentShoppingListDirections
            .actionCurrentShoppingListToShoppingListDetails())
    }

}