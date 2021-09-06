package com.pdstudios.shoppinglistfornetguru.database.item

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ItemDao {
    @Insert
    fun insertItem(item: ItemForm)

    @Update
    fun update(item: ItemForm)

    @Query("SELECT * FROM items_table WHERE itemID =:itemID")
    fun get(itemID: Long): ItemForm

    @Query("SELECT * FROM items_table ORDER BY itemID")
    fun getAll(): LiveData<List<ItemForm>>

    @Query("SELECT * FROM items_table WHERE listID =:listID ORDER BY itemID")
    fun getItemListFromShoppingList(listID: Long): LiveData<List<ItemForm>>

    @Query("DELETE FROM items_table WHERE itemID =:itemID")
    fun delete(itemID: Long)

    @Query("DELETE FROM items_table")
    fun clear()
}