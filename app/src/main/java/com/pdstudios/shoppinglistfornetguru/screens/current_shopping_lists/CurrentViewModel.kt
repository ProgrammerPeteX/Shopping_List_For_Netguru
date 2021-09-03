package com.pdstudios.shoppinglistfornetguru.screens.current_shopping_lists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CurrentViewModel: ViewModel() {

    private var _shoppingLists = MutableLiveData<MutableList<String>>()
    private var i = 1
    val shoppingLists: LiveData<MutableList<String>>
        get() = _shoppingLists

    var notifyAdapter = MutableLiveData<Boolean>()

    init {
        notifyAdapter.value = null
        _shoppingLists.value = mutableListOf()
    }

    fun onAddShoppingList() {
        _shoppingLists.value?.add(i++.toString())
        notifyAdapter.value = true
    }
}
