package com.example.se_al.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.se_al.R
import com.example.se_al.data.UserDatabase
import com.example.se_al.databinding.FragmentAlarmFixBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmFixFragment : Fragment() {

    private var _binding: FragmentAlarmFixBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAlarmFixBinding.inflate(inflater, container, false)
        val root: View = binding.root



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun setSpinnerClass(){

        val spinnerClass = binding.fixSpinnerClass

        try {
            val db = UserDatabase.getInstance(this.requireContext())

            CoroutineScope(Dispatchers.IO).launch { // 비동기
                val courseName : List<String> = listOf(db!!.courseDao().getCoursesName())
                val adapterClass = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, courseName)
                spinnerClass.adapter = adapterClass

                spinnerClass.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {}

                    override fun onItemSelected(
                        parent: AdapterView<*>?, view: View?, position: Int, id: Long,
                    ) {
                        Log.d(TAG, courseName[position])
                        //여기서 선택한 메뉴 이름 가지고 menuincourse
                        setSpinnerMenu(db, courseName[position] )

                    }
                }
            }
        } catch (e: java.lang.Exception) {
            println("Insert 에러 - $e")
        }
    }



    fun setSpinnerMenu(db : UserDatabase, courseName : String ){
        val spinnerMenu = binding.fixSpinnerMenu

        val menuInCourse  = db.menuDao().getMenuInCourseByName(courseName)
        val adapterMenu = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, menuInCourse)
        spinnerMenu.adapter = adapterMenu

        spinnerMenu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long,
            ) {
                Log.d(TAG, menuInCourse[position].toString())

            }
        }
    }

}