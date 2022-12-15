package com.kodego.todolist_firebased

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kodego.todolist_firebased.databinding.AboutAppBinding
import com.kodego.todolist_firebased.databinding.ActivityMainBinding
import com.kodego.todolist_firebased.databinding.EditDialogBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter : ToDoList_Adapter
    var dao = ToDoList_Dao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var calendar: Calendar = Calendar.getInstance()
        var simpleDateFormat = SimpleDateFormat("EEEE, MM-dd-yyyy hh:mm:ss")
        var dateTime = simpleDateFormat.format(calendar.time)

       // binding.txtvwToDoList.text = ToDoList_Database().toDoListDatabase.toString()

            //display table data on screen
            view()

            var textView: TextView = binding.edtxtDate
            textView.setText(dateTime)
            textView.isEnabled = false

            binding.btnAdd.setOnClickListener(){
                var toDoList:String = binding.edtxtToDoWork.text.toString()
                dao.add(toDoList)

                val workList = ToDoList(toDoList, dateTime)
                
                adapter.toDoListModel.add(workList)
                binding.edtxtToDoWork.text.clear()

                adapter.notifyDataSetChanged()

                Toast.makeText(applicationContext,"ADDED!", Toast.LENGTH_LONG).show()
            }

            binding.btnReset.setOnClickListener() {
                binding.btnView.isInvisible = false
                binding.btnReset.isInvisible = true
                adapter.toDoListModel.clear()
                binding.videoView.isVisible = true
                dao.delete("toDoList")
                adapter.notifyDataSetChanged()
            }

            binding.btnView.setOnClickListener() {
                binding.btnReset.isVisible = true
                binding.btnView.isVisible = false
                binding.videoView.isVisible = false
                view()
            }

            binding.vwAboutApp.setOnClickListener(){

                val dialogBox = Dialog(this)
                val binding: AboutAppBinding = AboutAppBinding.inflate(layoutInflater)
                dialogBox.setContentView(binding.root)
                dialogBox.show()

                binding.btnOKAY.setOnClickListener(){
                    dialogBox.dismiss()
                }
            }
        }


        private fun delete(){
            GlobalScope.launch(Dispatchers.IO) {
                dao.dbReference.removeValue()
                view()
            }
        }


        private fun view() {
            dao.get().addValueEventListener(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    var toDoList : ArrayList<ToDoList> = ArrayList<ToDoList>()
                    var dataFromDb = snapshot.children

                    for(data in dataFromDb){
                        var id = data.key.toString()
                        var toDoList = data.child("toDoList").value.toString()
                        var dateTime = data.child("toDoList").value.toString()
                        var doList = ToDoList(toDoList, dateTime)
                        doList
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        }

        @SuppressLint
        private fun updateData(){
            //GlobalScope.launch(Dispatchers.IO) {
                var mapData = mutableMapOf<String, String>()
                val binding:EditDialogBinding
                binding = EditDialogBinding.inflate(layoutInflater)
                val dialog = Dialog(this)
                dialog.setContentView(binding.root)
                mapData[""] = ""
                dao.update(key = "", mapData)
                dialog.show()

                binding.idBtnPickDate.setOnClickListener {
                    val c = Calendar.getInstance()
                    val year = c.get(Calendar.YEAR)
                    val month = c.get(Calendar.MONTH)
                    val day = c.get(Calendar.DAY_OF_MONTH)
                    val datePickerDialog = DatePickerDialog(
                        this,
                        { view, year, monthOfYear, dayOfMonth ->
                            binding.idTVSelectedDate.text =
                                (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                        },
                        year,
                        month,
                        day
                    )

                    datePickerDialog.show()

                }

                binding.btnOK.setOnClickListener(){
                    var newUpdate :String = binding.etToDoWork.text.toString()

                    GlobalScope.launch(Dispatchers.IO) {
                        dao.update(newUpdate,mapData)
                    }
                    dialog.dismiss()
                }
           // }
        }
}





