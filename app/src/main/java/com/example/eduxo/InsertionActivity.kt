package com.example.eduxo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var etcardnumber: EditText
    private lateinit var etamount: EditText
    private lateinit var etcvv: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etcardnumber = findViewById(R.id.etcardnumber)
        etamount = findViewById(R.id.etamount)
        etcvv = findViewById(R.id.etcvv)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Donate")

        btnSaveData.setOnClickListener {
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData() {

        //getting values
        val cardnumber = etcardnumber.text.toString()
        val amount = etamount.text.toString()
        val cvv= etcvv.text.toString()

        if (cardnumber.isEmpty()) {
            etcardnumber.error = "Please enter correct card number"
        }
        if (amount.isEmpty()) {
            etamount.error = "amount count by dollar($)"
        }
        if (cvv.isEmpty()) {
            etcvv.error = "Please enter cvv"
        }

        val userId = dbRef.push().key!!

        val employee = DonateModel(userId, cardnumber, amount, cvv)

        dbRef.child(userId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etcardnumber.text.clear()
                etamount.text.clear()
                etcvv.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}