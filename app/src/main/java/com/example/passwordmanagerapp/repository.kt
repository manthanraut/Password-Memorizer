package com.example.passwordmanagerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import java.util.Random
import kotlinx.android.synthetic.main.activity_repository.*

class repository : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository)
        val ss:String = intent.getStringExtra("passwdtype").toString()
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            // show back button on toolbar
            // on back button press, it will navigate to parent activity
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }
    fun saveRecord(view: View){
        val random = Random()
        val id= random.nextInt(50 - 1) + 1
        val type = acctype.text.toString()
        val uid=accusername.text.toString()
        val passwd = accpasswd.text.toString()
        val desc = accdesc.text.toString()
        val databaseHandler: DatabaseHandler= DatabaseHandler(this)
        if(type.trim()!="" && uid.trim()!="" && passwd.trim()!="" && desc.trim()!=""){
            val status = databaseHandler.addAccount(PasswordModelClass(id,type,uid,passwd, desc))
            if(status > -1){
                Toast.makeText(applicationContext,"Record saved",Toast.LENGTH_LONG).show()
                acctype.text.clear()
                accusername.text.clear()
                accpasswd.text.clear()
                accdesc.text.clear()
            }
        }else{
            Toast.makeText(applicationContext,"id or name or email cannot be blank",Toast.LENGTH_LONG).show()
        }

    }
}