package com.kodego.todolist_firebased

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ToDoList_Database {
        var toDoListDatabase: DatabaseReference = Firebase.database.getReference("todolist-firebased")
}