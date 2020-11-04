package kz.kolesateam.confapp.hello.presentation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

        val nameEditText: EditText = findViewById(R.id.activity_main_name_edit_text)
        val findHalloButton = findViewById<Button>(R.id.activity_main_open_hallo_button)

        nameEditText.addTextChangedListener { text ->
            if (text != null) {
                findHalloButton.isEnabled = text.isNotBlank()
            }
        }

        fun addingUserName() {
            val editor = sharedPreferences.edit()
            editor.putString(USER_NAME_KEY, nameEditText.text.toString())
            editor.apply()
        }
        fun startingTestActivity() {
            val intent = Intent(this, TestHelloActivity::class.java)
            startActivity(intent)
        }


        findHalloButton.setOnClickListener {
            if (nameEditText.text.isNotBlank()) {
                addingUserName()
                startingTestActivity()
            }
        }
    }
}