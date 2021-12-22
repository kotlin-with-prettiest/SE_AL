package com.example.a6_6.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class AppbarViewModel : ViewModel() {
    val currentNavController = MutableLiveData<NavController?>()

}