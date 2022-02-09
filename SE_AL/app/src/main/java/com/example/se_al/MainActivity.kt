package com.example.se_al

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import com.example.se_al.databinding.ActivityMainBinding
import com.example.se_al.login.LoginActivity
import com.example.se_al.worker.NotiWorker
import com.example.se_al.worker.UpdateDBWorker
import com.example.se_al.worker.UpdateDBWorker1
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

val TAG = "main"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val backgroundCoroutineScope = CoroutineScope(Dispatchers.Default)

    private val id = ""
    private val password = ""
    private val rtUrl = "blackboard.sejong.ac.kr"

    companion object {
        var instance: MainActivity? = null

        fun context(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_class_list, R.id.navigation_home_calendar, R.id.navigation_setting))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //delayCreateWork()

        delayedInit();
        //login()
        //noti()

    }

    private fun delayedInit() {
        backgroundCoroutineScope.launch {
            startUpdatingDB();
        }
    }

    private fun startUpdatingDB() {
        val workRequest = PeriodicWorkRequestBuilder<UpdateDBWorker>(15, TimeUnit.MINUTES).addTag("WIFIJOB1").build()
        WorkManager.getInstance(applicationContext).cancelAllWorkByTag("WIFIJOB1");
        WorkManager.getInstance(applicationContext).enqueue(workRequest)

//        val intent = Intent(this@SettingPersonalInfoFragment.context, LoginActivity::class.java)
//        startActivity(intent)
//        activity?.finish()
    }

//    fun CloseKeyboard() {
//        var view = this.currentFocus
//
//        if (view != null) {
//            val inputMethodManager =
//                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
//        }
//        Log.d(TAG, "hide keyboard")
//    }

    object flag {
        var home_flag : Int = 0
    }

    private fun delayCreateWork(){
        backgroundCoroutineScope.launch {
            createUpdateWorkManager()
            createNotiWorkManager()
        }
    }

    private fun createUpdateWorkManager(){
        val oneTimeWorkRequest = OneTimeWorkRequestBuilder<UpdateDBWorker1>().
        setInitialDelay(1, TimeUnit.MINUTES).build()

        Log.d("test", "Init WorkManager")
        WorkManager.getInstance(applicationContext).enqueueUniqueWork(UpdateDBWorker1.WORK_NAME, ExistingWorkPolicy.KEEP, oneTimeWorkRequest)
    }
    private fun createNotiWorkManager(){
        val oneTimeWorkRequest = OneTimeWorkRequestBuilder<NotiWorker>().
        setInitialDelay(1, TimeUnit.MINUTES).build()

        Log.d("Noti", "Init WorkManager")
        WorkManager.getInstance(applicationContext).enqueueUniqueWork(NotiWorker.WORK_NAME, ExistingWorkPolicy.KEEP, oneTimeWorkRequest)
    }


}

