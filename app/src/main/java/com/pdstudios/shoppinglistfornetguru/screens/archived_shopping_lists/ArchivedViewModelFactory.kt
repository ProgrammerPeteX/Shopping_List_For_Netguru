package com.pdstudios.shoppinglistfornetguru.screens.archived_shopping_lists

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pdstudios.shoppinglistfornetguru.database.shopping_list.ShoppingListsDao
import java.lang.IllegalArgumentException

class ArchivedViewModelFactory(
    private val shoppingListsDao: ShoppingListsDao,
    private val application: Application)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArchivedViewModel::class.java)) {
            return ArchivedViewModel(shoppingListsDao, application) as T
        }
        throw IllegalArgumentException("ViewModel not found!")
    }

}
