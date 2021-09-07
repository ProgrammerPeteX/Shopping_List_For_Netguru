package com.pdstudios.shoppinglistfornetguru.database.shopping_list

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ShoppingListsDao {
    @Insert
    fun insert(shoppingLists: ShoppingListsForm)

    @Update
    fun update(shoppingLists: ShoppingListsForm)

    @Query("SELECT * FROM shopping_lists_table WHERE listID =:listID")
    fun get(listID: Long): ShoppingListsForm

    @Query("SELECT * FROM shopping_lists_table ORDER BY listID DESC LIMIT 1")
    fun getLast(): ShoppingListsForm

    @Query("SELECT * FROM shopping_lists_table ORDER BY listID")
    fun getAll(): LiveData<List<ShoppingListsForm>>

    @Query("SELECT * FROM shopping_lists_table WHERE isArchived = 0 ORDER BY listID")
    fun getCurrentShoppingLists(): LiveData<List<ShoppingListsForm>>

    @Query("SELECT * FROM shopping_lists_table WHERE isArchived = 1 ORDER BY listID")
    fun getArchivedShoppingLists(): LiveData<List<ShoppingListsForm>>

    @Query("DELETE FROM shopping_lists_table WHERE listID =:listID")
    fun delete(listID: Long)

    @Query("DELETE FROM shopping_lists_table")
    fun clear()
}