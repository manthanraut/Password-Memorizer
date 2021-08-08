package com.example.passwordmanagerapp

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login_screen.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_repository.*

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setSupportActionBar(toolbarregister)
        supportActionBar?.apply {
            title="Create an account"
            // show back button on toolbar
            // on back button press, it will navigate to parent activity
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        link2.setOnClickListener{
            val intent = Intent(this,LoginScreen::class.java)
            startActivity(intent)
        }
        val mDatabase = FirebaseDatabase.getInstance()
        val mDatabaseReference = mDatabase!!.reference!!.child("Users")
        val mAuth = FirebaseAuth.getInstance()
        regbutton.setOnClickListener { createNewAccount() }
    }

    private fun createNewAccount() {
        var username=regusername.text.toString()
        var mobile=regmobile.text.toString()
        var password=regpassword.text.toString()
        var confirm_password=regconfirmpassword.text.toString()
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(password) &&!TextUtils.isEmpty(confirm_password)){
            if(TextUtils.isDigitsOnly(mobile) && mobile.length==10){
                if(TextUtils.equals(password,confirm_password)){
                    val mAuth = FirebaseAuth.getInstance()
                    mAuth!!
                        .createUserWithEmailAndPassword(username!!, password!!)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success")
                                val userId = mAuth!!.currentUser!!.uid
                                //Verify Email
                                //verifyEmail();
                                //update user profile information
                                val mDatabase = FirebaseDatabase.getInstance()
                                val mDatabaseReference = mDatabase!!.reference!!.child(userId)
                                //val currentUserDb = mDatabaseReference!!.child(userId)
                                mDatabaseReference.child("username").setValue(username)
                                mDatabaseReference.child("mobile").setValue(mobile)
                                mDatabaseReference.child("password").setValue(password)
                                updateUserInfoAndUI()
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                                Toast.makeText(this, "Authentication failed",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }

                }else{
                    Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Mobile number invalid", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }
    private fun updateUserInfoAndUI() {

        //start next activity
        val intent = Intent(this, LoginScreen::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}