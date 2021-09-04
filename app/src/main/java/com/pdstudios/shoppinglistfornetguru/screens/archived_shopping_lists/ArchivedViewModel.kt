package com.pdstudios.shoppinglistfornetguru.screens.archived_shopping_lists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ArchivedViewModel: ViewModel() {
    private var i = 1
    private var _shoppingLists = MutableLiveData<MutableList<String>>()
    val shoppingLists: LiveData<MutableList<String>>
        get() = _shoppingLists

    init {
        _shoppingLists.value = mutableListOf("1", "2", "3", "4", "5")
    }

}
