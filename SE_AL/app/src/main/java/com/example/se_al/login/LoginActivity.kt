package com.example.se_al.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.se_al.MainActivity
import com.example.se_al.R
import com.example.se_al.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*



class LoginActivity: AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_login)


        //로그인 버튼
        btn_login.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


    }



}

