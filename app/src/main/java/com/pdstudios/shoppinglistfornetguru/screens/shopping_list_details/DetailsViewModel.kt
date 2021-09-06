package com.pdstudios.shoppinglistfornetguru.screens.shopping_list_details

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.pdstudios.shoppinglistfornetguru.database.ShoppingDatabase
import com.pdstudios.shoppinglistfornetguru.database.details.DetailsDao
import com.pdstudios.shoppinglistfornetguru.database.details.DetailsForm
import com.pdstudios.shoppinglistfornetguru.database.shopping_list.ShoppingListsForm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(
    private val database: ShoppingDatabase,
    private val listID: Long,
    application: Application
): AndroidViewModel(application){

    private val detailsDao = database.detailsDao
    private val shoppingDao = database.shoppingListsDao

    val itemList = detailsDao.getItemList(listID)

    private var _shoppingList = MutableLiveData<ShoppingListsForm>()
    val shoppingList: LiveData<ShoppingListsForm>
        get() = _shoppingList

    init {
        viewModelScope.launch {
            _shoppingList.value = getShoppingList(listID)
        }
    }

    private suspend fun getShoppingList(listID: Long): ShoppingListsForm {
        return withContext(Dispatchers.IO) {
            shoppingDao.getShoppingList(listID)
        }
    }


//    fun getShoppingList(): ShoppingListsForm {
//        viewModelScope.launch {
//            val list = withContext(Dispatchers.IO) {
//                shoppingDao.getShoppingList(listID)
//            }
//            return list
//        }
//    }

    fun onAddItem() {
        viewModelScope.launch {
            val item = DetailsForm(listID = listID)
            updateDetails(item)
        }
    }

    private suspend fun updateDetails(item: DetailsForm) {
        withContext(Dispatchers.IO) {
            detailsDao.insertItem(item)
            Log.i("test", "updateDetails Works")
        }
    }

    fun updateItemList(item: DetailsForm) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                detailsDao.updateDetails(item)
            }
        }
    }

    fun delete(itemID: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                detailsDao.deleteFromItemList(itemID)
            }
        }
    }
}
