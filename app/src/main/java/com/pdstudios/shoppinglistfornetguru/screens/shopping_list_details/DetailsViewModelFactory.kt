package com.pdstudios.shoppinglistfornetguru.screens.shopping_list_details

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pdstudios.shoppinglistfornetguru.database.ShoppingDatabase
import java.lang.IllegalArgumentException

class DetailsViewModelFactory(
    private val shoppingDatabase: ShoppingDatabase,
    private val application: Application,
    private val listID: Long
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(shoppingDatabase,listID,application) as T
        }
        throw IllegalArgumentException("ViewModel not found!")
    }
}
