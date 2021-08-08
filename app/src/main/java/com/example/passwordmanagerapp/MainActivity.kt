package com.example.passwordmanagerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_list.*

class MainActivity : AppCompatActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logout.setOnClickListener {
            val intent = Intent(this, LoginScreen::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }
        addpassword.setOnClickListener{
            val intent = Intent(this, repository::class.java)
            startActivity(intent)
        }


        /*val arrayAdapter: ArrayAdapter<*>
        val list = arrayOf(
                "Cristiano Ronaldo",
                "Messi",
                "Neymar",
                "Isco",
                "Hazard",
                "Mbappe",
                "Hazard",
                "Ziyech",
                "Suarez"
        )
        // access the listView from xml file
        val listView = findViewById<ListView>(R.id.listView)
        arrayAdapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1, list
        )
        listView.adapter = arrayAdapter*/
        val databaseHandler: DatabaseHandler= DatabaseHandler(this)
        //calling the viewEmployee method of DatabaseHandler class to read the records
        val emp: List<PasswordModelClass> = databaseHandler.viewEmployee()
        val id = Array<String>(emp.size){"0"}
        val acctype = Array<String>(emp.size){"null"}
        val accid = Array<String>(emp.size){"null"}
        val accpasswd = Array<String>(emp.size){"null"}
        val accdesc=Array<String>(emp.size){"null"}
        var index = 0
        for(e in emp){
            id[index] = e.id.toString()
            acctype[index] = e.type.toString()
            accid[index]=e.uid.toString()
            accpasswd[index]=e.passwd.toString()
            accdesc[index]=e.desc.toString()
            index++
        }
        //val num = arrayOf<String>("1", "2", "3")
        val arrayAdapter: ArrayAdapter<*>
        arrayAdapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1, acctype
        )
        //creating custom ArrayAdapter
        val myListAdapter = MyListAdapter(this,id,acctype,accid,accpasswd,accdesc)
        listView.adapter = myListAdapter


    }

        private fun callActivity(type: String) {
            val intent = Intent(this, repository::class.java)
            intent.putExtra("passwdtype", type)
            startActivity(intent)
        }

}