package com.example.login_

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.login_.databinding.ActivitySignupBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class signup : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var text_login: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        binding = ActivitySignupBinding.inflate(layoutInflater)
    setContentView(binding.root)

        //provides authentication functionality using Firebase.
        firebaseAuth = FirebaseAuth.getInstance()

        binding.textView.setOnClickListener{
            val intent = Intent(this , signup::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener{

            val name = binding.etname.text.toString()
            val email = binding.emailet.text.toString()
            val phone = binding.etphone.text.toString()
            val pass = binding.etpassword.text.toString()
            val confirmpass = binding.etCpass.text.toString()


            //check whether all the filed are not empty
            if(name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && pass.isNotEmpty() && confirmpass.isNotEmpty()){

                //check whether the password and confirm password match
                if(pass == confirmpass){

                    firebaseAuth.createUserWithEmailAndPassword(email , pass).addOnCompleteListener{

                        //if user creation successfully,it launches the signup activity
                        if(it.isSuccessful){
                            val intent = Intent(this , signup::class.java)
                            startActivity(intent)

                        }else{
                            Toast.makeText(this , it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }


                    }

                    // If the password and confirm password do not match.this toast message will display
                }else{
                    Toast.makeText(this , "password is not matching" , Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this , "Empty Fields are not Allowed" , Toast.LENGTH_SHORT).show()
            }


        }
        //new added
        floatingActionButton = findViewById(R.id.floatingActionButton)

        floatingActionButton.setOnClickListener{

            val intent = Intent(this , login::class.java)
            startActivity(intent)
        }

//        text_login = findViewById(R.id.text_login)
//
//        text_login.setOnClickListener{
//
//            val intent = Intent(this , login::class.java)
//            startActivity(intent)
//        }



    }


}

