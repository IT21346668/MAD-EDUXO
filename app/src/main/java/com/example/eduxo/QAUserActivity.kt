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

class QAUserActivity : AppCompatActivity() {

    private lateinit var tvstId: TextView
    private lateinit var tvstName: TextView
    private lateinit var tvquestion: TextView
    private lateinit var tvanswer: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quetion_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("stId").toString(),
                intent.getStringExtra("question").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("stId").toString()
            )
        }

    }

    private fun initView() {
        tvstId = findViewById(R.id.tvstId)
        tvstName = findViewById(R.id.tvstName)
        tvquestion = findViewById(R.id.tvquestion)
        tvanswer = findViewById(R.id.tvanswer)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvstId.text = intent.getStringExtra("stId")
        tvstName.text = intent.getStringExtra("stName")
        tvquestion.text = intent.getStringExtra("question")
        tvanswer.text = intent.getStringExtra("answer")

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("QandA").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Question deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, QAFetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }


    private fun openUpdateDialog(
        userId: String,
        stname: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.activity_update, null)

        mDialog.setView(mDialogView)

        val etstName = mDialogView.findViewById<EditText>(R.id.etstName)
        val etquestion = mDialogView.findViewById<EditText>(R.id.etquestion)
        val etanswer = mDialogView.findViewById<EditText>(R.id.etanswer)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etstName.setText(intent.getStringExtra("stName").toString())
        etquestion.setText(intent.getStringExtra("question").toString())
        etanswer.setText(intent.getStringExtra("answer").toString())

        mDialog.setTitle("Updating $stname ")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateEmpData(
                userId,
                etstName.text.toString(),
                etquestion.text.toString(),
                etanswer.text.toString()
            )

            Toast.makeText(applicationContext, "Answer display successfully!!!", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textview
            tvstName.text = etstName.text.toString()
            tvquestion.text = etquestion.text.toString()
            tvanswer.text = etanswer.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateEmpData(
        stId: String,
        stname: String,
        que: String,
        aws: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("QandA").child(stId)
        val empInfo = QAModel(stId, stname, que, aws)
        dbRef.setValue(empInfo)
    }

}