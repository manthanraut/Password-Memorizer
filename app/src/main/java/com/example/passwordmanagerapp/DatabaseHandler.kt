package com.example.passwordmanagerapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteException

class DatabaseHandler(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "UserDatabase"
        private val TABLE_CONTACTS = "UserTable"
        private val KEY_ID = "id"
        private val KEY_TYPE="type"
        private val KEY_UID = "uid"
        private val KEY_PASSWD = "passwd"
        private val KEY_DESC = "desc"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //creating table with fields
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TYPE + " TEXT,"
                + KEY_UID + " TEXT," + KEY_PASSWD + " TEXT," + KEY_DESC + " TEXT" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS)
        onCreate(db)
    }


    //method to insert data
    fun addAccount(user: PasswordModelClass):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, user.id)
        contentValues.put(KEY_TYPE, user.type) //
        contentValues.put(KEY_UID,user.uid ) //
        contentValues.put(KEY_PASSWD, user.passwd) //
        contentValues.put(KEY_DESC,user.desc ) //
        // Inserting Row
        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    //method to read data

    fun viewEmployee():List<PasswordModelClass>{
        val userList:ArrayList<PasswordModelClass> = ArrayList<PasswordModelClass>()
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var userId: Int
        var usertype: String
        var useruid: String
        var userpasswd: String
        var userdesc: String
        if (cursor.moveToFirst()) {
            do {
                userId = cursor.getInt(cursor.getColumnIndex("id"))
                usertype = cursor.getString(cursor.getColumnIndex("type"))
                useruid = cursor.getString(cursor.getColumnIndex("uid"))
                userpasswd = cursor.getString(cursor.getColumnIndex("passwd"))
                userdesc = cursor.getString(cursor.getColumnIndex("desc"))
                val user= PasswordModelClass(id = userId, type = usertype, uid = useruid, passwd=userpasswd,desc=userdesc)
                userList.add(user)
            } while (cursor.moveToNext())
        }
        return userList
    }
    //method to update data

    fun updateEmployee(emp: PasswordModelClass):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.id)
        contentValues.put(KEY_TYPE, emp.type) // EmpModelClass Name
        contentValues.put(KEY_UID,emp.uid )
        contentValues.put(KEY_PASSWD, emp.passwd)
        contentValues.put(KEY_DESC, emp.desc) // EmpModelClass Email

        // Updating Row
        val success = db.update(TABLE_CONTACTS, contentValues,"id="+emp.id,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    //method to delete data
    fun deleteEmployee(user: PasswordModelClass):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, user.id) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_CONTACTS,"id="+user.id,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
}