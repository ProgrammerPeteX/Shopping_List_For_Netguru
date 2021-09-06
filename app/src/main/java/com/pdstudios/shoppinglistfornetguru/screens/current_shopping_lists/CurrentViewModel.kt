package com.pdstudios.shoppinglistfornetguru.screens.current_shopping_lists

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pdstudios.shoppinglistfornetguru.database.ShoppingDatabase
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

    private suspend fun insertShoppingList(shoppingList: ShoppingListsForm) {
        withContext(Dispatchers.IO) {
            shoppingListsDao.insertShoppingList(shoppingList)
        }
    }

    fun onTestClick() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                shoppingListsDao.clearShoppingLists()
            }
        }
    }

    fun deleteFromShoppingLists(listID: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                shoppingListsDao.deleteFromShoppingLists(listID)
            }
        }
    }

    fun updateShoppingLists(shoppingLists: ShoppingListsForm) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                shoppingListsDao.updateShoppingList(shoppingLists)
            }
        }
    }
}