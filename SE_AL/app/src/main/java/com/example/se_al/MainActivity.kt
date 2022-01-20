package com.example.se_al

import android.content.Context
import android.os.Bundle
import android.provider.DocumentsContract
import android.util.Log
import android.view.inputmethod.InputMethodManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import com.example.se_al.data.UserDatabase
import com.example.se_al.data.announcement.Announcement
import com.example.se_al.data.assignment.Assignment
import com.example.se_al.data.calendar.Calendar
import com.example.se_al.data.children_content.ChildrenContent
import com.example.se_al.data.content.Content
import com.example.se_al.data.course.Course
import com.example.se_al.data.exam.Exam
import com.example.se_al.data.lecture.Lecture
import com.example.se_al.data.menu.Menu
import com.example.se_al.data.sub_lecture.SubLecture
import com.example.se_al.data.user.User
import com.example.se_al.databinding.ActivityMainBinding
import com.example.se_al.worker.UpdateDBWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.DocumentType
import java.lang.Exception
import java.util.concurrent.TimeUnit

val TAG = "main"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val backgroundCoroutineScope = CoroutineScope(Dispatchers.Default)

    private val id = ""
    private val password = ""
    private val rtUrl = "blackboard.sejong.ac.kr"

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

        delayCreateWork()

        //login()
        //noti()


        var bb_id_input = intent.getStringExtra("bb_id_input")
        var bb_password_input = intent.getStringExtra("bb_password_input")
        Log.d(TAG, bb_id_input + bb_password_input)

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
            createWorkManager()
        }
    }

    private fun createWorkManager(){

        val oneTimeWorkRequest = OneTimeWorkRequestBuilder<UpdateDBWorker>().
        setInitialDelay(1, TimeUnit.MINUTES).build()

        Log.d("test", "Init WorkManager")
        WorkManager.getInstance(applicationContext).enqueueUniqueWork(UpdateDBWorker.WORK_NAME, ExistingWorkPolicy.KEEP, oneTimeWorkRequest)
    }


}

