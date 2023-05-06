package com.example.login_

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.login_.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class signup : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        binding = ActivitySignupBinding.inflate(layoutInflater)
    setContentView(binding.root)


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

            if(name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && pass.isNotEmpty() && confirmpass.isNotEmpty()){
                if(pass == confirmpass){

                    firebaseAuth.createUserWithEmailAndPassword(email , pass).addOnCompleteListener{
                        if(it.isSuccessful){
                            val intent = Intent(this , signup::class.java)
                            startActivity(intent)

                        }else{
                            Toast.makeText(this , it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }


                    }

                }else{
                    Toast.makeText(this , "password is not matching" , Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this , "Empty Fields are not Allowed" , Toast.LENGTH_SHORT).show()
            }


        }

    }


}

