package com.kodego.todolist_firebased

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.kodego.todolist_firebased.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var calendar: Calendar = Calendar.getInstance()
        var simpleDateFormat = SimpleDateFormat("EEEE, MM-dd-yyyy hh:mm:ss")
        var dateTime = simpleDateFormat.format(calendar.time)
        var textView: TextView = findViewById(R.id.edtxtDate)


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            toDoListdb = ToDoDatabase.invoke(this)

            //display table data on screen
            view()

            textView.setText(dateTime)
            textView.isEnabled = false

            binding.btnAdd.setOnClickListener(){
                var worklist:String = binding.edtxtToDoWork.text.toString()
                val workList = WorkList(worklist)


                add(workList)
                adapter.worklistModel.add(workList)
                binding.edtxtToDoWork.text.clear()

                adapter.notifyDataSetChanged()

                Toast.makeText(applicationContext,"ADDED!", Toast.LENGTH_LONG).show()
            }

            binding.btnReset.setOnClickListener() {
                binding.btnView.isInvisible = false
                binding.btnReset.isInvisible = true
                adapter.worklistModel.clear()
                binding.videoView.isVisible = true
                clearAllTask()
                adapter.notifyDataSetChanged()
            }

            binding.btnView.setOnClickListener() {
                binding.btnReset.isVisible = true
                binding.btnView.isVisible = false
                binding.videoView.isVisible = false
                view()
            }

            binding.vwAboutApp.setOnClickListener(){
                // aboutApp()
                val dialogBox = Dialog(this)
                val binding: AboutappBinding = AboutappBinding.inflate(layoutInflater)
                dialogBox.setContentView(binding.root)
                dialogBox.show()

                binding.btnOKAY.setOnClickListener(){
                    dialogBox.dismiss()
                }
            }
        }