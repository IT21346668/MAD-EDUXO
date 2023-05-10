package com.example.eduxo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class DonateUserActivity : AppCompatActivity() {

    private lateinit var tvuserId: TextView
    private lateinit var tvcardnumber: TextView
    private lateinit var tvamount: TextView
    private lateinit var tvcvv: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate_user_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("userId").toString(),
                intent.getStringExtra("amount").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("userId").toString()
            )
        }

    }

    private fun initView() {
        tvuserId = findViewById(R.id.tvuserId)
        tvcardnumber = findViewById(R.id.tvcardnumber)
        tvamount = findViewById(R.id.tvamount)
        tvcvv = findViewById(R.id.tvcvv)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvuserId.text = intent.getStringExtra("userId")
        tvcardnumber.text = intent.getStringExtra("cardnumber")
        tvamount.text = intent.getStringExtra("amount")
        tvcvv.text = intent.getStringExtra("cvv")

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Donate").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Employee data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }


    private fun openUpdateDialog(
        userId: String,
        cnumber: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.activity_update, null)

        mDialog.setView(mDialogView)

        val etcardnumber = mDialogView.findViewById<EditText>(R.id.etcardnumber)
        val etamount = mDialogView.findViewById<EditText>(R.id.etamount)
        val etcvv = mDialogView.findViewById<EditText>(R.id.etcvv)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etcardnumber.setText(intent.getStringExtra("cardnumber").toString())
        etamount.setText(intent.getStringExtra("amount").toString())
        etcvv.setText(intent.getStringExtra("cvv").toString())

        mDialog.setTitle("Updating $cnumber Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateEmpData(
                userId,
                etcardnumber.text.toString(),
                etamount.text.toString(),
                etcvv.text.toString()
            )

            Toast.makeText(applicationContext, "Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textview
            tvcardnumber.text = etcardnumber.text.toString()
            tvamount.text = etamount.text.toString()
            tvcvv.text = etcvv.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateEmpData(
        id: String,
        cnumber: String,
        payment: String,
        ccc: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Donate").child(id)
        val empInfo = DonateModel(id, cnumber, payment, ccc)
        dbRef.setValue(empInfo)
    }

}