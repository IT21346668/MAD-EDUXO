package com.example.login_

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.login_.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class login : AppCompatActivity() {

    private  lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var loginbtn:Button
    private lateinit var btnResetPassword:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.textView.setOnClickListener {
            val intent = Intent(this , login::class.java)
            startActivity(intent)
        }

        val button = findViewById<Button>(R.id.loginbtn) // declare and initialize new the button
        button.setOnClickListener{
            val intent = Intent(this@login,signup::class.java)
            startActivity(intent)}
        binding.button.setOnClickListener {

            val email = binding.emailet.text.toString()
            val pass = binding.etpassword.text.toString()


            if( email.isNotEmpty() &&  pass.isNotEmpty() ){


                    firebaseAuth.signInWithEmailAndPassword(email , pass).addOnCompleteListener{
                        if(it.isSuccessful){
                            val intent = Intent(this , MainActivity::class.java)
                            startActivity(intent)

                        }else{
                            Toast.makeText(this , it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }


                    }


            }else{
                Toast.makeText(this , "Empty Fields are not Allowed" , Toast.LENGTH_SHORT).show()
            }


       }
        //example
        btnResetPassword = findViewById(R.id.resetPassword)

        btnResetPassword.setOnClickListener{

            val intent = Intent(this , ResetPasswordActivity::class.java)
            startActivity(intent)
        }




    }
}