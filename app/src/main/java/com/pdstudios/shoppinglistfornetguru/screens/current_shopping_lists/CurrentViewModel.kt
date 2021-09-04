package com.pdstudios.shoppinglistfornetguru.screens.current_shopping_lists

import android.app.Application
import android.util.Log
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

class CurrentViewModel(shoppingDatabase: ShoppingDatabase, application: Application)
    : AndroidViewModel(application) {

    private val shoppingListsDao = shoppingDatabase.shoppingListsDao
    private val detailsDao= shoppingDatabase.detailsDao

    var finished = MutableLiveData<Boolean>()

    private var _shoppingLists: MutableLiveData<MutableList<ShoppingListsForm>>
    = MutableLiveData(mutableListOf())

    val shoppingLists: LiveData<MutableList<ShoppingListsForm>>
        get() = _shoppingLists

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
            _shoppingLists.value = initialiseShoppingList()
            finished.value = true
        }
    }

    private suspend fun insertShoppingList(shoppingList: ShoppingListsForm) {
        withContext(Dispatchers.IO) {
            shoppingListsDao.insertShoppingList(shoppingList)
        }
    }

    private suspend fun initialiseShoppingList(): MutableList<ShoppingListsForm>? {
        return withContext(Dispatchers.IO) {
            shoppingListsDao.getArchivedShoppingLists().value
        }
    }



}
