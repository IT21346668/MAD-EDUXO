package com.example.login_

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.login_.R.id.button_1
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Success : AppCompatActivity() {

    private lateinit var button_1:Button
    private lateinit var floatingActionButton2: FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)




        //new added
        button_1 = findViewById(R.id.button_1)

        button_1.setOnClickListener{

            val intent = Intent(this , login::class.java)
            startActivity(intent)

        }

        //new fab
        floatingActionButton2 = findViewById(R.id.floatingActionButton2)

        floatingActionButton2.setOnClickListener{

            val intent = Intent(this , ResetPasswordActivity::class.java)
            startActivity(intent)

        }

    }
}