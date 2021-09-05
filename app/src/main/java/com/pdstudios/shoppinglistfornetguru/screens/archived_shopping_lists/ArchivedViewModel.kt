package com.pdstudios.shoppinglistfornetguru.screens.archived_shopping_lists

import android.app.Application
import androidx.lifecycle.*
import com.pdstudios.shoppinglistfornetguru.database.details.DetailsDao
import com.pdstudios.shoppinglistfornetguru.database.shopping_list.ShoppingListsDao
import com.pdstudios.shoppinglistfornetguru.database.shopping_list.ShoppingListsForm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArchivedViewModel(
    private val shoppingListsDao: ShoppingListsDao,
    application: Application
): AndroidViewModel(application) {
    val shoppingLists = shoppingListsDao.getArchivedShoppingLists()

    fun deleteFromShoppingLists(listID: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                shoppingListsDao.deleteFromShoppingLists(listID)
            }
        }
    }

    fun updateShoppingLists(shoppingLists: ShoppingListsForm) {
        viewModelScope.launch {
            updateShoppingListsSuspend(shoppingLists)
        }
    }

    private suspend fun updateShoppingListsSuspend(shoppingLists: ShoppingListsForm) {
        withContext(Dispatchers.IO) {
            shoppingListsDao.updateShoppingList(shoppingLists)
        }
    }
}
