package com.kodego.todolist_firebased

import android.graphics.Typeface
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.kodego.todolist_firebased.databinding.RowItemBinding
import java.time.Instant
import java.util.*

class ToDoList_Adapter (var toDoListModel: MutableList<ToDoList>): RecyclerView.Adapter<ToDoList_Adapter.ToDoListViewHolder>(){

    var onItemDelete : ((ToDoList, Int) -> Unit) ? = null
    var onEdit : ((ToDoList, Int) -> Unit) ? = null
    var onView : ((ToDoList, Int) -> Unit) ? = null


    inner class ToDoListViewHolder(var binding: RowItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowItemBinding.inflate(layoutInflater, parent, false)
        return ToDoListViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ToDoListViewHolder, position: Int) {
        holder.binding.apply{
            txtvwToDoWork.text = toDoListModel[position].toDoList
            txtvwDate.text = Date.from(Instant.now()).toString()

            imgbtnDelete.setOnClickListener(){
                onItemDelete?.invoke(toDoListModel[position],position)
            }
            imgbtnEdit.setOnClickListener(){
                onEdit?.invoke(toDoListModel[position],position)
            }
            view.setOnClickListener(){
                onView?.invoke(toDoListModel[position],position)
                txtvwToDoWork.setTypeface(Typeface.DEFAULT_BOLD, Typeface.NORMAL)
                imgbtnEdit.isEnabled = false
                txtvwToDoWork.isAllCaps = true
                txtvwToDoWork.setText("Done")
                view.isInvisible = true

            }
        }
    }

    override fun getItemCount(): Int {
        return toDoListModel.size
    }

}
    
