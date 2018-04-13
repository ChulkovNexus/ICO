package com.icoalert.ui.activitypager

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import com.icoalert.R
import com.icoalert.databinding.ActivityMainBinding
import com.icoalert.ui.BaseViewModelActivity
import com.icoalert.ui.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.view.MenuItemCompat
import android.widget.SearchView


class MainActivity : BaseViewModelActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var binding : ActivityMainBinding
    lateinit var toggle : ActionBarDrawerToggle
    lateinit var mainActivitiyViewModel: MainActivitiyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = mainActivitiyViewModel
        setSupportActionBar(binding.toolbar)

        toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        if (savedInstanceState==null) {
            nav_view.menu.findItem(R.id.nav_ico)?.isChecked = true
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        searchView.setOnQueryTextListener(mainActivitiyViewModel)
        searchView.setOnCloseListener(mainActivitiyViewModel)
        return true
    }

    override fun createViewModel(): BaseViewModel {
        mainActivitiyViewModel = MainActivitiyViewModel(this)
        return mainActivitiyViewModel
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_filter -> return true
            R.id.action_search -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_ico -> mainActivitiyViewModel.setIcoType(false)
            R.id.nav_preico -> mainActivitiyViewModel.setIcoType(true)
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
