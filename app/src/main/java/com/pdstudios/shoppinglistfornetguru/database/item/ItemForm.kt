package com.pdstudios.shoppinglistfornetguru.database.item

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items_table")
data class ItemForm(
    @PrimaryKey(autoGenerate = true)
    var itemID: Long = 0L,
    var listID: Long = 0L,

    @ColumnInfo
    var name: String = "ITEM",

    @ColumnInfo
    var isChecked: Boolean = false
)
