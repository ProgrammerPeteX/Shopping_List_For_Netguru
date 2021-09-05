package com.pdstudios.shoppinglistfornetguru.database.shopping_list

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_lists_table")
data class ShoppingListsForm(

    @PrimaryKey(autoGenerate = true)
    var listID: Long = 0L,

    @ColumnInfo
    var name: String = "New Shopping List details $listID",

    @ColumnInfo
    var isArchived: Boolean = false,

    @ColumnInfo
    var dateCreated: Long = System.currentTimeMillis()
)
