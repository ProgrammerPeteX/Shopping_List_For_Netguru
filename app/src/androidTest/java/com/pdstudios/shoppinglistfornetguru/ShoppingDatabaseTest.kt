package com.pdstudios.shoppinglistfornetguru

import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pdstudios.shoppinglistfornetguru.database.ShoppingDatabase
import com.pdstudios.shoppinglistfornetguru.database.item.ItemDao
import com.pdstudios.shoppinglistfornetguru.database.shopping_list.ShoppingListsDao
import com.pdstudios.shoppinglistfornetguru.database.shopping_list.ShoppingListsForm
import com.pdstudios.shoppinglistfornetguru.screens.archived_shopping_lists.ArchivedViewModel
import com.pdstudios.shoppinglistfornetguru.screens.current_shopping_lists.CurrentViewModel
import com.pdstudios.shoppinglistfornetguru.screens.item_list.ItemsViewModel
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ShoppingDatabaseTest {
    private lateinit var shoppingListsDao: ShoppingListsDao
    private lateinit var itemDao: ItemDao
    private lateinit var db: ShoppingDatabase
    private lateinit var currentViewModel: CurrentViewModel
    private lateinit var archivedViewModel: ArchivedViewModel
    private lateinit var itemsViewModel: ItemsViewModel
    @Before
    fun createDatabase() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, ShoppingDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        shoppingListsDao = db.shoppingListsDao
        itemDao = db.itemDao
        //How can i get my viewModel in?
//        currentViewModel = ViewModelProvider(this).get(CurrentViewModel::class.java)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }


    //Template
    @Test
    @Throws(IOException::class)
    fun template() {
        val testName = "TEST"
        val shoppingList = ShoppingListsForm(name = testName)
        shoppingListsDao.insert(shoppingList)
        val expected = ""
        val actual = ""
        assertEquals(expected, actual)
    }

    @Test
    @Throws(IOException::class)
    fun insertAndGetShoppingList() {
        val testName = "TEST"
        val shoppingList = ShoppingListsForm(name = testName)
        shoppingListsDao.insert(shoppingList)
        val expected = testName
        val actual = shoppingListsDao.getLast().name
        assertEquals(expected, actual)
    }

    @Test
    @Throws(IOException::class)
    fun updateShoppingList() {
        var shoppingList = ShoppingListsForm(name = "XXXXX")
        shoppingListsDao.insert(shoppingList)
        val testName = "TEST"
        shoppingList = shoppingListsDao.getLast()
        shoppingList.name = testName
        shoppingListsDao.update(shoppingList)
        val expected = "TEST"
        val actual = shoppingListsDao.getLast().name
        assertEquals(expected, actual)
    }

    @Test
    @Throws(IOException::class)
    fun deleteFromShoppingList() {
        val shoppingList = ShoppingListsForm()
        shoppingListsDao.insert(shoppingList)
        val listId = shoppingListsDao.getLast().listID
        shoppingListsDao.delete(listId)
        val expected = null
        val actual = shoppingListsDao.get(listId)
        assertEquals(expected, actual)
    }

}