package com.pdstudios.shoppinglistfornetguru.screens.archived_shopping_lists

import android.text.InputType
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pdstudios.shoppinglistfornetguru.databinding.ShoppingListCardBinding
import com.pdstudios.shoppinglistfornetguru.screens.current_shopping_lists.CurrentShoppingListDirections

class ArchivedRecyclerAdapter(
    private var list: LiveData<MutableList<String>>
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
        holder.shoppingListName.text = list.value!![position]

        holder.cardView.setOnClickListener { view ->
            if (!isLongClick) navigateToDetails(view)
        }

        holder.cardView.setOnLongClickListener {
            isLongClick = true
            holder.shoppingListName.visibility = View.GONE
            holder.editShoppingListName.visibility = View.VISIBLE
            holder.shoppingListName.inputType = InputType.TYPE_CLASS_TEXT
            holder.editShoppingListName.setText(list.value!![position])
            true
        }

        holder.editShoppingListName.setOnKeyListener { view, keyCode, keyEvent ->
            var bool = false
            if (keyEvent.action == KeyEvent.ACTION_DOWN &&
                keyCode == KeyEvent.KEYCODE_ENTER) {
                isLongClick = false
                holder.editShoppingListName.visibility = View.GONE
                holder.shoppingListName.visibility = View.VISIBLE
                list.value!![position] = holder.editShoppingListName.text.toString()
                holder.shoppingListName.text = holder.editShoppingListName.text
                bool = true
            }
            bool
        }
    }

    override fun getItemCount(): Int {
        return list.value!!.size
    }

    inner class ViewHolder(itemView: View):
        RecyclerView.ViewHolder(itemView) {
        var shoppingListName = binding.textViewShoppingListName
        var editShoppingListName = binding.editTextShoppingListName
        var cardView = binding.cardView
    }

    private fun navigateToDetails(view: View) {
        view.findNavController().navigate(
            ArchivedShoppingListDirections
            .actionArchivedShoppingListToShoppingListDetails())
    }

}

