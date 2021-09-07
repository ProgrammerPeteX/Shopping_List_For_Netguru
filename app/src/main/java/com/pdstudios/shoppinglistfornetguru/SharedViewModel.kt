package com.pdstudios.shoppinglistfornetguru

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {
    var actionBarTitle = MutableLiveData<String>()
}
