package com.pdstudios.shoppinglistfornetguru.screens.current_shopping_lists

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pdstudios.shoppinglistfornetguru.database.ShoppingDatabase
import com.pdstudios.shoppinglistfornetguru.database.shopping_list.ShoppingListsForm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrentViewModel(
    shoppingDatabase: ShoppingDatabase,
    application: Application)
    : AndroidViewModel(application) {

    private val shoppingListsDao = shoppingDatabase.shoppingListsDao

    var finished = MutableLiveData<Boolean>()

    val shoppingLists = shoppingListsDao.getCurrentShoppingLists()


    var notifyAdapter = MutableLiveData<Boolean>()

    init {

        notifyAdapter.value = null
        finished.value = null

    }

    fun onAddShoppingList() {
        viewModelScope.launch {
            notifyAdapter.value = true

            val shoppingList = ShoppingListsForm()
            insertShoppingList(shoppingList)
            finished.value = true
        }
    }

    private suspend fun insertShoppingList(shoppingList: ShoppingListsForm) {
        withContext(Dispatchers.IO) {
            shoppingListsDao.insertShoppingList(shoppingList)
        }
    }

    private suspend fun initialiseShoppingList(): LiveData<List<ShoppingListsForm>> {
        return withContext(Dispatchers.IO) {
            shoppingListsDao.getAllShoppingLists()
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
            updateShoppingListsSuspend(shoppingLists)
        }
    }

    private suspend fun updateShoppingListsSuspend(shoppingLists: ShoppingListsForm) {
        withContext(Dispatchers.IO) {
            shoppingListsDao.updateShoppingList(shoppingLists)
        }
    }

}