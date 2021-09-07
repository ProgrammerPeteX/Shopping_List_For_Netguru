package com.pdstudios.shoppinglistfornetguru.screens.current_shopping_lists

import android.content.Context
import android.text.InputType
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pdstudios.shoppinglistfornetguru.database.shopping_list.ShoppingListsForm
import com.pdstudios.shoppinglistfornetguru.databinding.ShoppingListCardBinding

open class CurrentRecyclerAdapter(
    private var lists: LiveData<List<ShoppingListsForm>>,
    private val adapterListener: CurrentRecyclerAdapter.AdapterListener
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
        val shoppingList = lists.value!![position]
        holder.shoppingListName.text = shoppingList.name
        val inputManager: InputMethodManager =
            holder.itemView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        holder.cardView.setOnClickListener { view ->
            if (!isLongClick) navigateToItems(view, shoppingList.listID, shoppingList.name)
        }

        holder.cardView.setOnLongClickListener { view ->
            isLongClick = true
            holder.shoppingListName.visibility = View.GONE
            holder.editShoppingListName.visibility = View.VISIBLE
            holder.shoppingListName.inputType = InputType.TYPE_CLASS_TEXT
            holder.editShoppingListName.setText(shoppingList.name)
            holder.editShoppingListName.requestFocus()
            inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
            true
        }

        holder.editShoppingListName.setOnKeyListener { view, keyCode, keyEvent ->

            var bool = false
            if (keyEvent.action == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER) {
                isLongClick = false
                holder.editShoppingListName.visibility = View.GONE
                holder.shoppingListName.visibility = View.VISIBLE
                shoppingList.name = holder.editShoppingListName.text.toString()
                adapterListener.updateShoppingLists(shoppingList)
                inputManager.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0)
                bool = true
            }
            bool
        }
    }

    override fun getItemCount(): Int {
        return lists.value?.size ?: 0
    }

    inner class ViewHolder(itemView: View):
        RecyclerView.ViewHolder(itemView) {
            var shoppingListName = binding.textViewShoppingListName
            var editShoppingListName = binding.editTextShoppingListName
            var cardView = binding.cardView
        }

    private fun navigateToItems(view: View, listID: Long, listName: String) {
        view.findNavController().navigate(CurrentShoppingListDirections
            .actionCurrentShoppingListToShoppingListItems(listID, listName))
    }

    interface AdapterListener {
        fun updateShoppingLists(shoppingLists: ShoppingListsForm)
    }
}