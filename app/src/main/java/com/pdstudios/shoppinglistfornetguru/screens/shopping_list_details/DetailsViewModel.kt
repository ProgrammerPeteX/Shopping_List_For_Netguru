package com.pdstudios.shoppinglistfornetguru.screens.shopping_list_details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailsViewModel: ViewModel(){
    private var i = 1
    private var _itemList = MutableLiveData<MutableList<String>>()
    val itemList: LiveData<MutableList<String>>
        get() = _itemList

    var notifyAdapter = MutableLiveData<Boolean>()

    init {
        notifyAdapter.value = null
        _itemList.value = mutableListOf()
    }

    fun onAddItem() {
        _itemList.value?.add(0,i++.toString())
        notifyAdapter.value = true
        Log.i("test", "works")
    }
}
