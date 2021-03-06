package com.kiran.student.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kiran.student.R
import com.kiran.student.adapter.StudentAdapter
import com.kiran.student.repository.StudentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class ViewStudentActivity : AppCompatActivity() {

    private lateinit var recyclerview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_student)

        recyclerview = findViewById(R.id.recyclerview)
        getAllStudents()

    }
    private fun getAllStudents(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val studentRepository = StudentRepository()
                val response = studentRepository.getAllStudents()
                if(response.success == true){
                    //Student details in listStudents
                        val listStudents = response.data
                    withContext(Main){
                        recyclerview.adapter = StudentAdapter( listStudents!!, this@ViewStudentActivity,)
                        recyclerview.layoutManager = LinearLayoutManager(this@ViewStudentActivity)
                    }
                }

            }catch (ex: Exception){
                withContext(Main){
                    Toast.makeText(this@ViewStudentActivity,
                        "Error : ${ex.toString()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}