package com.pdstudios.shoppinglistfornetguru.screens.item_list

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.pdstudios.shoppinglistfornetguru.database.ShoppingDatabase
import com.pdstudios.shoppinglistfornetguru.database.item.ItemForm
import com.pdstudios.shoppinglistfornetguru.database.shopping_list.ShoppingListsForm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemsViewModel(
    private val database: ShoppingDatabase,
    private val listID: Long,
    application: Application
): AndroidViewModel(application){

    private val itemListDao = database.itemDao
    private val shoppingListDao = database.shoppingListsDao

    val itemList = itemListDao.getItemListFromShoppingList(listID)

    private var _shoppingList = MutableLiveData<ShoppingListsForm>()
    val shoppingLists: LiveData<ShoppingListsForm>
        get() = _shoppingList

    init {
        viewModelScope.launch {
            _shoppingList.value = getShoppingList(listID)
        }
    }

    private suspend fun getShoppingList(listID: Long): ShoppingListsForm {
        return withContext(Dispatchers.IO) {
            shoppingListDao.get(listID)
        }
    }
    
    fun onAddItem() {
        viewModelScope.launch {
            val item = ItemForm(listID = listID)
            updateItem(item)
        }
    }

    private suspend fun updateItem(item: ItemForm) {
        withContext(Dispatchers.IO) {
            itemListDao.insertItem(item)
            Log.i("test", "updateItem Works")
        }
    }

    fun updateItemList(item: ItemForm) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                itemListDao.update(item)
            }
        }
    }

    fun delete(itemID: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                itemListDao.delete(itemID)
            }
        }
    }
}
