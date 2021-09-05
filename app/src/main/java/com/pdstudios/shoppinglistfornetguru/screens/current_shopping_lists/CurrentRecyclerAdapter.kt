package com.pdstudios.shoppinglistfornetguru.screens.current_shopping_lists

import android.text.InputType
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pdstudios.shoppinglistfornetguru.database.shopping_list.ShoppingListsDao
import com.pdstudios.shoppinglistfornetguru.database.shopping_list.ShoppingListsForm
import com.pdstudios.shoppinglistfornetguru.databinding.ShoppingListCardBinding

open class CurrentRecyclerAdapter(
    private var list: LiveData<List<ShoppingListsForm>>,
    private val adapterListener: AdapterListener
): RecyclerView.Adapter<CurrentRecyclerAdapter.ViewHolder>(){

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

        holder.cardView.setOnLongClickListener {
            isLongClick = true
            holder.shoppingListName.visibility = View.GONE
            holder.editShoppingListName.visibility = View.VISIBLE
            holder.shoppingListName.inputType = InputType.TYPE_CLASS_TEXT
            holder.editShoppingListName.setText(list.value!![position].name)
            true
        }

        holder.editShoppingListName.setOnKeyListener { view, keyCode, keyEvent ->

            var bool = false
            if (keyEvent.action == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER) {
                isLongClick = false
                holder.editShoppingListName.visibility = View.GONE
                holder.shoppingListName.visibility = View.VISIBLE
                list.value!![position].name = holder.editShoppingListName.text.toString()

//                shoppingListsDao.updateShoppingList(list.value!![position])
//                updateShoppingLists(position)
                val shoppingList = list.value!![position]
                adapterListener.updateShoppingList(shoppingList)
                holder.shoppingListName.text = holder.editShoppingListName.text
                bool = true
            }
            bool
        }
    }



    override fun getItemCount(): Int {
        return list.value?.size ?: 0
    }

    inner class ViewHolder(itemView: View):
        RecyclerView.ViewHolder(itemView) {
            var shoppingListName = binding.textViewShoppingListName
            var editShoppingListName = binding.editTextShoppingListName
            var cardView = binding.cardView
        }

    private fun navigateToDetails(view: View) {
        view.findNavController().navigate(CurrentShoppingListDirections
            .actionCurrentShoppingListToShoppingListDetails())
    }

    interface AdapterListener {
        fun updateShoppingList(shoppingLists: ShoppingListsForm)
    }
}