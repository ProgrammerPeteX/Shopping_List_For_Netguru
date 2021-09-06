package com.pdstudios.shoppinglistfornetguru.database.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DetailsDao {
    @Insert
    fun insertItem(details: DetailsForm)

    @Update
    fun updateDetails(details: DetailsForm)

    @Query("SELECT * FROM details_table WHERE itemID =:itemID")
    fun getItem(itemID: Long): DetailsForm

    @Query("SELECT * FROM details_table ORDER BY itemID")
    fun getAllItems(): LiveData<List<DetailsForm>>

    @Query("SELECT * FROM details_table WHERE listID =:listID ORDER BY itemID")
    fun getItemList(listID: Long): LiveData<List<DetailsForm>>

    @Query("DELETE FROM details_table WHERE itemID =:itemID")
    fun deleteFromItemList(itemID: Long)

    @Query("DELETE FROM details_table")
    fun clearItemList()
}