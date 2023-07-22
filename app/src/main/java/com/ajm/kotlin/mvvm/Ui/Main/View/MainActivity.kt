package com.ajm.kotlin.mvvm.Ui.Main.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ajm.kotlin.mvvm.R
import com.ajm.kotlin.mvvm.Ui.Main.ViewModel.MainActivityViewModel
import com.ajm.kotlin.mvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setupNavController();
        initview()
    }
    private fun setupNavController() {
        navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        navController = navHostFragment.navController

    }
    private fun initview() {
         viewModel= ViewModelProvider(this).get(MainActivityViewModel::class.java)

        navController.navigate(R.id.usertListFragment)

    }
}