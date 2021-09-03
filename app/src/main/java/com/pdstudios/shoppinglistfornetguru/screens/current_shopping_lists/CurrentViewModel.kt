package com.pdstudios.shoppinglistfornetguru.screens.current_shopping_lists

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CurrentViewModel: ViewModel() {

    private var _shoppingLists = MutableLiveData<MutableList<Int>>()
    private var i = 0
    val shoppingLists: LiveData<MutableList<Int>>
        get() = _shoppingLists

    var notifyAdapater = MutableLiveData<Boolean>()

    init {
        notifyAdapater.value = null
        _shoppingLists.value = mutableListOf(999,2,3,4,5,6,7,8,9,112,233,434,654,457,8786,6786,678,867867,44,5,64,46,2)
    }

    fun onAddShoppingList() {
        _shoppingLists.value?.add(i++)
        notifyAdapater.value = true
    }
}
