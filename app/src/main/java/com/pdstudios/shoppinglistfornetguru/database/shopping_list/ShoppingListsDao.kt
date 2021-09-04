package com.pdstudios.shoppinglistfornetguru.database.shopping_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ShoppingListsDao {
    @Insert
    fun insertShoppingList(shoppingList: ShoppingListsForm)

    @Update
    fun updateShoppingList(shoppingList: ShoppingListsForm)

    @Query("SELECT * FROM shopping_lists_table WHERE listID =:listID")
    fun getShoppingList(listID: Long): ShoppingListsForm

    @Query("SELECT * FROM shopping_lists_table ORDER BY listID")
    fun getAllShoppingLists(): LiveData<MutableList<ShoppingListsForm>>

    @Query("SELECT * FROM shopping_lists_table WHERE isArchived = 1 ORDER BY listID")
    fun getCurrentShoppingLists(): LiveData<MutableList<ShoppingListsForm>>

    @Query("SELECT * FROM shopping_lists_table WHERE isArchived = 0 ORDER BY listID")
    fun getArchivedShoppingLists(): LiveData<MutableList<ShoppingListsForm>>

    @Query("DELETE FROM shopping_lists_table WHERE listID =:listID")
    fun deleteFromShoppingLists(listID: Long)

    @Query("DELETE FROM shopping_lists_table")
    fun clearShoppingLists()
}