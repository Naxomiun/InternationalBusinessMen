package com.nramos.internationalbusinessmen

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.nramos.internationalbusinessmen.databinding.ActivityMainBinding
import com.nramos.internationalbusinessmen.delegates.collectInLifeCycle
import com.nramos.internationalbusinessmen.ui.common.adapters.TransactionsAdapter
import com.nramos.internationalbusinessmen.ui.screens.transaction.viewmodel.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding //TODO Futurible: use delegates for viewBinding

    private val navController by lazy {
        Navigation.findNavController(this, R.id.nav_host_fragment_content_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean { //TODO Futurible: control backstack if more graphs are added
        navController.navigateUp()
        return super.onSupportNavigateUp()
    }

}