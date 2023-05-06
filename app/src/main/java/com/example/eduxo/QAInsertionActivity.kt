package com.example.eduxo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class QAInsertionActivity : AppCompatActivity() {

    private lateinit var etstName: EditText
    private lateinit var etquestion: EditText
    private lateinit var etanswer: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etstName = findViewById(R.id.etstName)
        etquestion = findViewById(R.id.etquestion)
        etanswer = findViewById(R.id.etanswer)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("QandA")

        btnSaveData.setOnClickListener {
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData() {

        //getting values
        val stName = etstName.text.toString()
        val question = etquestion.text.toString()
        val answer= etanswer.text.toString()

        if (stName.isEmpty()) {
            etstName.error = "Please enter your name"
        }
        if (question.isEmpty()) {
            etquestion.error = "Enter your question here"
        }
        if (answer.isEmpty()) {
            etanswer.error = "Please enter your answer"
        }

        val userId = dbRef.push().key!!

        val employee = QAModel(userId, stName, question, answer)

        dbRef.child(userId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this, "Question Sent successfully", Toast.LENGTH_LONG).show()

                etstName.text.clear()
                etquestion.text.clear()
                etanswer.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}