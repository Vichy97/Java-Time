package com.vincent.javatime

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val navController: NavController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_JavaTime)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupToolbar()
        //setupDrawerLayout()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navController)
    }

    private fun setupDrawerLayout() {
        nav_view.setupWithNavController(navController)

        nav_view.setNavigationItemSelectedListener {

            drawer_layout.closeDrawers()

            when (it.itemId) {
                R.id.menu_item_about -> {
                    navController.navigate(R.id.aboutFragment)
                    true
                }
                R.id.menu_item_favorites -> {
                    navController
                    true
                }
                else -> { false }
            }
        }
    }

//    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp(drawer_layout)
//    }
}
