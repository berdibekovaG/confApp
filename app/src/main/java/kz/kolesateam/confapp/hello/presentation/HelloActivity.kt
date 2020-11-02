package kz.kolesateam.confapp.hello.presentation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import kz.kolesateam.confapp.R


private const val TAG = "HelloActivity"
const val APPLICATION_SHARED_PREFERENCES = "Name"
 const val USER_NAME_KEY = "name"

class HelloActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)
        val sharedPreferences: SharedPreferences = getSharedPreferences(
                APPLICATION_SHARED_PREFERENCES,
                Context.MODE_PRIVATE

        )

   val editText: EditText = findViewById(R.id.activity_main_name_edit_text)

    editText.addTextChangedListener{ text ->
        findViewById<Button>(R.id.activity_main_open_hallo_button).isEnabled = !text.isNullOrEmpty()
   }
//
////        val userName: String = getSavedUserName()
////
//    }

  //  private fun getSavedUserName(): String{

        findViewById<Button>(R.id.activity_main_open_hallo_button).setOnClickListener {
           if (!editText.text.isNullOrEmpty()) {
               val editor = sharedPreferences.edit()
               editor.putString(USER_NAME_KEY, editText.text.toString())
               editor.apply()
               val intent = Intent(this, TestHelloActivity::class.java)
               startActivity(intent)}

        }


      // return sharedPreferences.getString(USER_NAME_KEY, "Мир") ?: "Мир"
    }
}