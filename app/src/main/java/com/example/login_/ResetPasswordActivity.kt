package com.example.login_

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var etPassword:EditText
    private lateinit var btnReset:Button
    //new
    private lateinit var Fab_Back:FloatingActionButton

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        etPassword = findViewById(R.id.et_reset_email)
        btnReset = findViewById(R.id.btn_reset)

        firebaseAuth = FirebaseAuth.getInstance()


        btnReset.setOnClickListener {

            val sPassword =  etPassword.text.toString()
            firebaseAuth.sendPasswordResetEmail(sPassword)
                .addOnSuccessListener {
                    Toast.makeText(this, "plaese check your Email" , Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{
                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                }
        }

        //example
        Fab_Back = findViewById(R.id.Fab_Back)

        Fab_Back.setOnClickListener{

            val intent = Intent(this , login::class.java)
            startActivity(intent)
        }


    }
}