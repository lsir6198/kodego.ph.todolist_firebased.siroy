package com.kodego.todolist_firebased

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ToDoList_Dao {
    var dbReference: DatabaseReference = Firebase.database.reference

    fun add(toDoList: String){
        dbReference.push().setValue(toDoList)
    }

    fun get(): Query {
        return dbReference.orderByKey()
    }

    fun delete(key:String){
        dbReference.child(key).removeValue()
    }

    fun update(key: String, map:Map<String,String>){
        dbReference.child(key).updateChildren(map)
    }

}