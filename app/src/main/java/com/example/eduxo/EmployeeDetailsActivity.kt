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

class EmployeeDetailsActivity : AppCompatActivity() {

    private lateinit var tvcourseId: TextView
    private lateinit var tvcoursename: TextView
    private lateinit var tvteachername: TextView
    private lateinit var tvcoursecode: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("courseId").toString(),
                intent.getStringExtra("coursename").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("courseId").toString()
            )
        }

    }

    private fun initView() {
        tvcourseId = findViewById(R.id.tvcourseId)
        tvcoursename = findViewById(R.id.tvcoursename)
        tvteachername = findViewById(R.id.tvteachername)
        tvcoursecode = findViewById(R.id.tvcoursecode)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvcourseId.text = intent.getStringExtra("courseId")
        tvcoursename.text = intent.getStringExtra("coursename")
        tvteachername.text = intent.getStringExtra("teachername")
        tvcoursecode.text = intent.getStringExtra("coursecode")

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("course").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Employees data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(
        courseId: String,
        coursename: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val etcoursename = mDialogView.findViewById<EditText>(R.id.etcoursename)
        val etteachername = mDialogView.findViewById<EditText>(R.id.etteachername)
        val etcoursecode = mDialogView.findViewById<EditText>(R.id.etcoursecode)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etcoursename.setText(intent.getStringExtra("coursename").toString())
        etteachername.setText(intent.getStringExtra("teachername").toString())
        etcoursecode.setText(intent.getStringExtra("coursecode").toString())

        mDialog.setTitle("Updating $coursename Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateEmpData(
                courseId,
                etcoursename.text.toString(),
                etteachername.text.toString(),
                etcoursecode.text.toString()
            )

            Toast.makeText(applicationContext, "Employee Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvcoursename.text = etcoursename.text.toString()
            tvteachername.text = etteachername.text.toString()
            tvcoursecode.text = etcoursecode.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateEmpData(
        id: String,
        name: String,
        teachername: String,
        coursecode: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("course").child(id)
        val empInfo = EmployeeModel(id, name, teachername, coursecode)
        dbRef.setValue(empInfo)
    }

}