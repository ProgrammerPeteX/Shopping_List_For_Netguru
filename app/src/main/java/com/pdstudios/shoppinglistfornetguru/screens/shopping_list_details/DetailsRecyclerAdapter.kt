package com.pdstudios.shoppinglistfornetguru.screens.shopping_list_details

import android.text.InputType
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.pdstudios.shoppinglistfornetguru.databinding.DetailsCardBinding


class DetailsRecyclerAdapter(
    private var list: LiveData<MutableList<String>>
):
    RecyclerView.Adapter<DetailsRecyclerAdapter.ViewHolder>() {

    private lateinit var binding: DetailsCardBinding
    private var isLongClick = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DetailsCardBinding.inflate(inflater,parent,false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemName.text = list.value!![position]

        holder.cardView.setOnClickListener {
            if (!isLongClick) tickCheckbox(holder)
        }

        holder.cardView.setOnLongClickListener {
            isLongClick = true
            holder.itemName.visibility = View.GONE
            holder.editItemName.visibility = View.VISIBLE
            holder.itemName.inputType = InputType.TYPE_CLASS_TEXT
            holder.editItemName.setText(list.value!![position])
            true
        }

        holder.editItemName.setOnKeyListener { view, keyCode, keyEvent ->
            var bool = false
            if (keyEvent.action == KeyEvent.ACTION_DOWN &&
                keyCode == KeyEvent.KEYCODE_ENTER) {
                isLongClick = false
                holder.editItemName.visibility = View.GONE
                holder.itemName.visibility = View.VISIBLE
                list.value!![position] = holder.editItemName.text.toString()
                holder.itemName.text = holder.editItemName.text
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
        var itemName = binding.textViewItemName
        var editItemName = binding.editTextItemName
        var cardView = binding.cardViewDetails
        var checkBox = binding.checkBox
    }

    private fun tickCheckbox(holder: ViewHolder) {
        holder.checkBox.isChecked = when (holder.checkBox.isChecked) {
            true -> false
            false -> true
        }
    }
}