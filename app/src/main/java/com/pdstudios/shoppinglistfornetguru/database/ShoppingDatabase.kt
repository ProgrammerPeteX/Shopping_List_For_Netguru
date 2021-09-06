package com.pdstudios.shoppinglistfornetguru.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pdstudios.shoppinglistfornetguru.database.item.ItemDao
import com.pdstudios.shoppinglistfornetguru.database.item.ItemForm
import com.pdstudios.shoppinglistfornetguru.database.shopping_list.ShoppingListsDao
import com.pdstudios.shoppinglistfornetguru.database.shopping_list.ShoppingListsForm

@Database(entities =
[ShoppingListsForm::class, ItemForm::class],
    version = 5, exportSchema = false)

abstract class ShoppingDatabase: RoomDatabase() {

    abstract val shoppingListsDao: ShoppingListsDao
    abstract val itemDao: ItemDao

    companion object {

        @Volatile
        private var INSTANCE: ShoppingDatabase? = null

        fun getInstance(context: Context): ShoppingDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ShoppingDatabase::class.java,
                    "shopping_database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}