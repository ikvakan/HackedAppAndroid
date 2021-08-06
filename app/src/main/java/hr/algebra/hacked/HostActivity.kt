package hr.algebra.hacked


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView

import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_host.*

class HostActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        setContentView(R.layout.activity_host)

        initHamburgerMenu()
        initNavigation()
    }

    private fun initHamburgerMenu() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                toggleDrawer()
                return true
            }
            R.id.menuExit ->{
                exitApp()
                return true
            }

        }
        return super.onOptionsItemSelected(item)

    }




    private fun exitApp() {
        AlertDialog.Builder(this).apply {
            setTitle(R.string.exit)
            setMessage(getString(R.string.do_you_want_to_exit))
            setIcon(R.drawable.ic_baseline_exit_to_app_24)
            setPositiveButton("OK") { _, _ -> finish() }
            setNegativeButton("Cancel",null)
            show()
        }
    }

    private fun toggleDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawers()
        }else{
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun initNavigation() {
        val navController = Navigation.findNavController(this, R.id.navHostFragment)
        NavigationUI.setupWithNavController(navigationView,navController)
    }
}