package com.pdstudios.shoppinglistfornetguru.screens.current_shopping_lists

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pdstudios.shoppinglistfornetguru.database.shopping_list.ShoppingListsDao
import com.pdstudios.shoppinglistfornetguru.database.shopping_list.ShoppingListsForm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrentViewModel(
    private val shoppingListsDao: ShoppingListsDao,
    application: Application)
    : AndroidViewModel(application) {

    val shoppingLists = shoppingListsDao.getCurrentShoppingLists()

    fun onAddShoppingList() {
        viewModelScope.launch {
            val shoppingList = ShoppingListsForm()
            insertShoppingList(shoppingList)
        }
    }

    private suspend fun insertShoppingList(shoppingLists: ShoppingListsForm) {
        withContext(Dispatchers.IO) {
            shoppingListsDao.insert(shoppingLists)
        }
    }

    fun deleteFromShoppingLists(listID: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                shoppingListsDao.delete(listID)
            }
        }
    }

    fun updateShoppingLists(shoppingLists: ShoppingListsForm) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                shoppingListsDao.update(shoppingLists)
            }
        }
    }
}