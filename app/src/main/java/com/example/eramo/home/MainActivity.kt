package com.example.eramo.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.eramo.R
import com.example.eramo.core.presentation.base.BaseActivity
import com.example.eramo.core.presentation.dialog.CloseAppDialog
import com.example.eramo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var navController: NavController

    var id = R.id.fragmentHome

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
    }


    private fun initialize() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                R.id.fragmentHome -> {
                  id = R.id.fragmentHome
                }
                R.id.fragmentPostDetails->{
                    id = R.id.fragmentPostDetails
                }

            }
        }

    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        if(id == R.id.fragmentHome) {
            CloseAppDialog {
                finish()
            }.show(supportFragmentManager, "")
        }else{
            navController.navigateUp()
        }
    }

}