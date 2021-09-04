package com.pdstudios.shoppinglistfornetguru.database.details

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.pdstudios.shoppinglistfornetguru.database.shopping_list.ShoppingListsForm

@Entity(tableName = "details_table")
data class DetailsForm(
    @PrimaryKey(autoGenerate = true)
    var itemID: Long = 0L,
    var listID: Long = 0L,

    @ColumnInfo
    var name: String = "Shopping Item",

    @ColumnInfo
    var isChecked: Boolean = false
)
