package com.pdstudios.shoppinglistfornetguru.screens.current_shopping_lists

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pdstudios.shoppinglistfornetguru.database.shopping_list.ShoppingListsDao
import java.lang.IllegalArgumentException

class CurrentViewModelFactory(
    private val shoppingDatabase: ShoppingListsDao,
    private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrentViewModel::class.java)) {
            return CurrentViewModel(shoppingDatabase, application) as T
        }

        throw IllegalArgumentException("ViewModel not found!")
    }

}
