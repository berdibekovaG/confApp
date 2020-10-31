package kz.kolesateam.confapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kz.kolesateam.confapp.hello.presentation.HelloActivity

private const val TAG = "MainActivity"
const val USER_NAME_KEY = "user_name"
const val APPLICATION_SHARED_PREFERENCES= "application"


    class MainActivity : AppCompatActivity() {
        private val openHalloButton: Button by lazy {
            findViewById(R.id.activity_main_open_hallo_button)
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            setContentView(R.layout.activity_main)


            val nameEditText: EditText = findViewById(R.id.activity_main_name_edit_text)

            openHalloButton.setOnClickListener {
                saveUserName(nameEditText.text.toString())
                navigateToHelloScreen()
            }
        }

        private fun saveUserName(userName: String){
            val sharedPreferences: SharedPreferences = getSharedPreferences(
                    APPLICATION_SHARED_PREFERENCES,
                    Context.MODE_PRIVATE
            )
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(USER_NAME_KEY, userName)
            editor.apply()
        }

        private fun navigateToHelloScreen(){
            val halloScreenIntent= Intent(this, HelloActivity::class.java)
            startActivity(halloScreenIntent)
        }


    }