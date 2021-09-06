package com.pdstudios.shoppinglistfornetguru.screens.item_list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pdstudios.shoppinglistfornetguru.database.ShoppingDatabase
import java.lang.IllegalArgumentException

class ItemsViewModelFactory(
    private val shoppingDatabase: ShoppingDatabase,
    private val application: Application,
    private val listID: Long
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemsViewModel::class.java)) {
            return ItemsViewModel(shoppingDatabase,listID,application) as T
        }
        throw IllegalArgumentException("ViewModel not found!")
    }
}
