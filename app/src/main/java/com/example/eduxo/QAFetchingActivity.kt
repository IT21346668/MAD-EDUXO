package com.example.eduxo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*


class QAFetchingActivity : AppCompatActivity() {

    private lateinit var empRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var empList: ArrayList<QAModel>
    private lateinit var dbRef: DatabaseReference
    private lateinit var btnInsertData : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        btnInsertData = findViewById(R.id.btnInsertData)

        empRecyclerView = findViewById(R.id.rvEmp)
        empRecyclerView.layoutManager = LinearLayoutManager(this)
        empRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        empList = arrayListOf<QAModel>()

        getEmployeesData()

        btnInsertData.setOnClickListener {
            val intent = Intent(this, QAInsertionActivity::class.java)
            startActivity(intent)
        }


    }


    private fun getEmployeesData() {

        empRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("QandA")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                empList.clear()
                if (snapshot.exists()){
                    for (empSnap in snapshot.children){
                        val empData = empSnap.getValue(QAModel::class.java)
                        empList.add(empData!!)
                    }
                    val mAdapter = QAdapter(empList)
                    empRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : QAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@QAFetchingActivity, QAUserActivity::class.java)

                            //put extras
                            intent.putExtra("stId", empList[position].stId)
                            intent.putExtra("stName", empList[position].stName)
                            intent.putExtra("question", empList[position].question)
                            intent.putExtra("answer", empList[position].answer)
                            startActivity(intent)
                        }

                    })

                    empRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }
}