package com.example.seal.ui.main

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.seal.R
import com.example.seal.databinding.FragmentClassListBinding


class ClassListFragment : BaseDataBindingFragment<FragmentClassListBinding>() {

    override fun getLayoutRes(): Int = R.layout.fragment_class_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        dataBinding!!.class1.blockingClickListener {
//            findNavController().navigateUp()
//            findNavController().navigate(ClassListFragmentDirections.actionClassListFragmentToClassBaseFragment())
//        }
v
//        dataBinding!!.class1.setOnClickListener {
////            performDoubleClick()
//        }


        dataBinding!!.class1.setOnClickListener {
            val action = ClassListFragmentDirections.actionClassListFragmentToClassBaseFragment()
            findNavController().navigateUp()
            findNavController().navigate(action)
        }

        
    }

//    private val clickTag = "__click__"
//
//    fun View.blockingClickListener(debounceTime: Long = 1200L, action: () -> Unit) {
//        this.setOnClickListener(object : View.OnClickListener {
//            private var lastClickTime: Long = 0
//            override fun onClick(v: View) {
//                val timeNow = SystemClock.elapsedRealtime()
//                val elapsedTimeSinceLastClick = timeNow - lastClickTime
//                Log.d(clickTag, """
//                        DebounceTime: $debounceTime
//                        Time Elapsed: $elapsedTimeSinceLastClick
//                        Is within debounce time: ${elapsedTimeSinceLastClick < debounceTime}
//                    """.trimIndent())
//
//                if (elapsedTimeSinceLastClick < debounceTime) {
//                    Log.d(clickTag, "Double click shielded")
//                    return
//                } else {
//                    Log.d(clickTag, "Click happened")
//                    action()
//                }
//                lastClickTime = SystemClock.elapsedRealtime()
//            }
//        })
//    }
//
//    private fun performDoubleClick() {
//        for (i in 1 downTo 0) {
//            findNavController().navigate(ClassListFragmentDirections.actionClassListFragmentToClassBaseFragment())
//        }
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_class_base, container, false)
    }

}