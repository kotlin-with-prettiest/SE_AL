package com.example.se_al.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.se_al.MainActivity
import com.example.se_al.R
import com.example.se_al.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.doAsync
 

val TAG = "Login"
 

class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        loginLayout.setOnClickListener {
            CloseKeyboard()
        }



        //로그인 버튼
        btn_login.setOnClickListener {

            // 입력된 아이디 비번 값을 가져와서 로그인(계정) DB 생성
            // 계정 정보(아이디, 비번)는 LoginUIS 에서 직접 가져와서 쓰기

            //참이면 로그인 성공한거
           doAsync {
           if(firstBlackBoardLogin()){
               Log.d("로그인","성공")
           }
           else{//로그인 실패 처리
               Log.d("로그인","실패")
           }}
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            Log.d(TAG, "main Activity")
            CloseKeyboard()

//            login()

        }
    }


    fun CloseKeyboard() {
        var view = this.currentFocus

        if (view != null) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
        Log.d(TAG, "hide keyboard")

    }




}

