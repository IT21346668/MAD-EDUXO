package com.example.eduxo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var etcoursename: EditText
    private lateinit var etteachername: EditText
    private lateinit var etcoursecode: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etcoursename = findViewById(R.id.etcoursename)
        etteachername = findViewById(R.id.etteachername)
        etcoursecode = findViewById(R.id.etcoursecode)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("course")

        btnSaveData.setOnClickListener {
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData() {

        //getting values
        val coursename = etcoursename.text.toString()
        val teachername = etteachername.text.toString()
        val coursecode = etcoursecode.text.toString()

        if (coursename.isEmpty()) {
            etcoursename.error = "Please enter name"
        }
        if (teachername.isEmpty()) {
            etteachername.error = "Please enter age"
        }
        if (coursecode.isEmpty()) {
            etcoursecode.error = "Please enter salary"
        }

        val empId = dbRef.push().key!!

        val employee = EmployeeModel(empId, coursename, teachername, coursecode)

        dbRef.child(empId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etcoursename.text.clear()
                etteachername.text.clear()
                etcoursecode.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}