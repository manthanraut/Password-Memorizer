package com.example.passwordmanagerapp

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import kotlinx.android.synthetic.main.custom_list.*

class MyListAdapter(private val context: Activity,private val id: Array<String>,private val title: Array<String>,private val userid: Array<String>,private val userpasswd: Array<String>,private val userdesc:Array<String>)
    : ArrayAdapter<String>(context, R.layout.custom_list, title) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_list, null, true)
        val titleText = rowView.findViewById(R.id.acctype) as TextView
        val del_btn = rowView.findViewById(R.id.delete_btn) as ImageButton
        val view_btn = rowView.findViewById(R.id.view_btn) as ImageButton
        val update_btn=rowView.findViewById(R.id.edit_btn) as ImageButton


        update_btn.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(context)
            val inflater = this.context.layoutInflater
            val dialogView = inflater.inflate(R.layout.update_dialog, null)
            val newaccid=dialogView.findViewById(R.id.updateaccid) as EditText
            val newaccpasswd=dialogView.findViewById(R.id.updateaccpasswd) as EditText
            val newaccdesc=dialogView.findViewById(R.id.updateaccdesc) as EditText

            dialogBuilder.setView(dialogView)
            dialogBuilder.setTitle("Update your account details")
            dialogBuilder.setPositiveButton("Update", DialogInterface.OnClickListener { _, _ ->
                val updateId = newaccid.text.toString()
                val updatepasswd = newaccpasswd.text.toString()
                val updatedesc = newaccdesc.text.toString()
                //creating the instance of DatabaseHandler class
                val databaseHandler: DatabaseHandler= DatabaseHandler(context)
                if(updateId.trim()!="" && updatepasswd.trim()!="" && updatedesc.trim()!=""){
                    //calling the updateEmployee method of DatabaseHandler class to update record
                    val status = databaseHandler.updateEmployee(PasswordModelClass(Integer.parseInt(id[position]),title[position],updateId,updatepasswd,updatedesc))
                    if(status > -1){
                        Toast.makeText(context,"Record updated successfully",Toast.LENGTH_LONG).show()
                        val intent = Intent(context,MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        getContext().startActivity(intent)
                    }
                }else{
                    Toast.makeText(context,"Field cannot be kept blank",Toast.LENGTH_LONG).show()
                }

            })
            dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                //pass
            })
            val b = dialogBuilder.create()
            b.show()
        }
        del_btn.setOnClickListener {
            val databaseHandler: DatabaseHandler = DatabaseHandler(context)
            if (titleText.text.trim() != "") {
                //calling the deleteEmployee methodtit of DatabaseHandler class to delete record
                val status = databaseHandler.deleteEmployee(PasswordModelClass(Integer.parseInt(id[position]), "", "", "", ""))
                if (status > -1) {
                    val intent = Intent(context,MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    getContext().startActivity(intent)
                    //Toast.makeText(context, "Record deleted", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(context, "id or name or email cannot be blank", Toast.LENGTH_LONG).show()
            }
        }
            view_btn.setOnClickListener {
                val dialogBuilder = AlertDialog.Builder(context)
                val inflater = this.context.layoutInflater
                //val dialogView = inflater.inflate(R.layout.view_dialog, null)
                //dialogBuilder.setView(dialogView)
                dialogBuilder.setTitle("Your account details")
                val msg="Acc. type : "+title[position]+"\n"+"Acc. ID : "+userid[position]+"\n"+"Acc. password : "+userpasswd[position]+"\n"+"Acc. Description : "+userdesc[position]
                dialogBuilder.setMessage(msg)
                dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                    //pass
                })
                val b = dialogBuilder.create()
                b.show()
/*                Toast.makeText(context, "View",
                        Toast.LENGTH_SHORT).show()*/
            }
/*
        val dialogBuilder = AlertDialog.Builder(context)
        val dialogView = inflater.inflate(R.layout.view_dialog, null)
        dialogBuilder.setView(dialogView)
        dialogBuilder.setTitle("Your account details")
        val msg=title[position]+userid[position]+userpasswd[position]
        dialogBuilder.setMessage(msg)
        val b = dialogBuilder.create()
        b.show()*/
        titleText.text = title[position]

            return rowView
        }
}