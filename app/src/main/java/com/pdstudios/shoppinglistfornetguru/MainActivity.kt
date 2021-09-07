package com.pdstudios.shoppinglistfornetguru

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.pdstudios.shoppinglistfornetguru.screens.current_shopping_lists.CurrentShoppingList
import java.lang.reflect.Array.newInstance


class MainActivity : AppCompatActivity() {

    private val sharedViewModel: SharedViewModel by viewModels()
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedViewModel.actionBarTitle.observe(this) { title ->
            supportActionBar?.title = title
        }


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment)
        val navController = navHostFragment!!.findNavController()
        appBarConfiguration = AppBarConfiguration(navController.graph)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            val currentDest = destination.id == R.id.currentShoppingList
            val archivedDest = destination.id == R.id.archivedShoppingList
            if (currentDest || archivedDest) {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.navHostFragment)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
}