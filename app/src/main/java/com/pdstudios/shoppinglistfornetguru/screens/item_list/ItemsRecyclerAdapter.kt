package com.pdstudios.shoppinglistfornetguru.screens.item_list

import android.text.InputType
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.pdstudios.shoppinglistfornetguru.database.item.ItemForm
import com.pdstudios.shoppinglistfornetguru.database.shopping_list.ShoppingListsForm
import com.pdstudios.shoppinglistfornetguru.databinding.ItemsCardBinding


class ItemsRecyclerAdapter(
    private var itemList: LiveData<List<ItemForm>>,
    private val shoppingLists: LiveData<ShoppingListsForm>,
    private val adapterListener: AdapterListener
):
    RecyclerView.Adapter<ItemsRecyclerAdapter.ViewHolder>() {

    private lateinit var binding: ItemsCardBinding
    private var isLongClick = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemsCardBinding.inflate(inflater,parent,false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList.value!![position]
        holder.itemName.text = item.name
        holder.checkBox.isChecked = item.isChecked

        Log.i("test", "-\n" +
                "name = ${shoppingLists.value?.name}\n" +
                "isArchived = ${shoppingLists.value?.isArchived}\n")
        if (shoppingLists.value?.isArchived == false) {
            //checkbox
            if (!isLongClick) {
                holder.cardView.setOnClickListener {
                    item.isChecked = when (holder.checkBox.isChecked) {
                        true -> false
                        false -> true
                    }
                    holder.checkBox.isChecked = item.isChecked
                    adapterListener.updateItemList(item)
                }

                holder.checkBox.setOnClickListener {
                    item.isChecked = holder.checkBox.isChecked
                    holder.checkBox.isChecked = item.isChecked
                    adapterListener.updateItemList(item)
                }
            }

            //enableChangeName
            holder.cardView.setOnLongClickListener {
                isLongClick = true
                holder.itemName.visibility = View.GONE
                holder.editItemName.visibility = View.VISIBLE
                holder.itemName.inputType = InputType.TYPE_CLASS_TEXT
                holder.editItemName.setText(item.name)
                true
            }

            //changeName
            holder.editItemName.setOnKeyListener { view, keyCode, keyEvent ->
                var bool = false
                if (keyEvent.action == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    isLongClick = false
                    holder.editItemName.visibility = View.GONE
                    holder.itemName.visibility = View.VISIBLE
                    item.name = holder.editItemName.text.toString()
                    adapterListener.updateItemList(item)
                    bool = true
                }
                bool
            }
        }
         else {
            holder.checkBox.isEnabled = false
        }
    }

    override fun getItemCount(): Int {
        return itemList.value?.size ?: 0
    }

    inner class ViewHolder(itemView: View):
        RecyclerView.ViewHolder(itemView) {
        var itemName = binding.textViewItemName
        var editItemName = binding.editTextItemName
        var cardView = binding.cardViewItems
        var checkBox = binding.checkBox
    }

    interface AdapterListener {
        fun updateItemList(item: ItemForm)
    }
}