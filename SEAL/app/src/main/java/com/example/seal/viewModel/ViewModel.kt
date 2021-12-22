package com.example.seal.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class ViewModel : ViewModel() {
    val currentNavController = MutableLiveData<NavController?>()
}