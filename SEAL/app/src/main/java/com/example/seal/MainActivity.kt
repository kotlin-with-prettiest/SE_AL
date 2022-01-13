package com.example.seal

import android.content.res.Resources
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.tabs.TabLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.seal.viewModel.ViewModel
import com.example.seal.adapter.ActivityFragmentStateAdapter
import com.example.seal.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        listenBackStackChange()
    }


    private fun listenBackStackChange() {
        // Get NavHostFragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)

        // ChildFragmentManager of NavHostFragment
        val navHostChildFragmentManager = navHostFragment?.childFragmentManager

        navHostChildFragmentManager?.addOnBackStackChangedListener {

            val backStackEntryCount = navHostChildFragmentManager.backStackEntryCount
            val fragments = navHostChildFragmentManager.fragments
            val fragmentCount = fragments.size

            println("🎃 Main graph backStackEntryCount: $backStackEntryCount, fragmentCount: $fragmentCount, fragments: $fragments")

            Toast.makeText(
                this,
                "🎃 Main graph backStackEntryCount: $backStackEntryCount, fragmentCount: $fragmentCount, fragments: $fragments",
                Toast.LENGTH_SHORT
            ).show()
        }
    }



//    private val viewModel: ViewModel by viewModels()
//    private lateinit var binding: ActivityMainBinding
//    private lateinit var navController: NavController
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val sectionsPagerAdapter = ActivityFragmentStateAdapter(this)
////        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, 3)
//
//        val viewPager2: ViewPager2 = binding.viewPager
//
//        // Set viewpager adapter
//        viewPager2.adapter = sectionsPagerAdapter
//
//        val tabs: TabLayout = binding.tabs
//        val res: Resources = getResources();
//        val titles: Array<String> = res.getStringArray(R.array.tab_title);
//
//        TabLayoutMediator(tabs, viewPager2) { tab, position ->
//            tab.text = titles[position]
//        }.attach()
//
////
////        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
////            override fun onTabReselected(tab: TabLayout.Tab?) {}
////            override fun onTabUnselected(tab: TabLayout.Tab?) {}
////            override fun onTabSelected(tab: TabLayout.Tab?) {
////                when (tabs.selectedTabPosition) {
////                    0 -> navController.navigate(R.id.classListFragment)
////                    1 -> navController.navigate(R.id.nav_graph_home)
////                    2 -> navController.navigate(R.id.settingFragment)
////                }
////            }
////        })
//
//
//
////
////////여기부분 문제임 tablayout에 네브컨트롤러연결
////////그리고 프래그먼트 다시 확인하기
////        viewModel.currentNavController.observe(this, Observer { it ->
////            it?.let { event: Event<NavController> ->
////                event.getContentIfNotHandled()?.let { navController ->
////                    val appBarConfig = AppBarConfiguration(navController.graph)
////                    binding.tabs.setupWithNavController(navController, appBarConfig)  //여기 해결해얗ㅁㄴ
////                }
////            }
////        })
//
////
////        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nestedHomeNavHostFragment) as NavHostFragment //error
////        navController = navHostFragment.navController
////        setupActionBarWithNavController(navController)
//
//    }
//
//
//    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp() || super.onSupportNavigateUp()
//    }

}
