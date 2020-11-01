package kz.kolesateam.confapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import kz.kolesateam.confapp.hello.presentation.HelloActivity

const val USER_NAME_KEY = "user_name"
const val APPLICATION_SHARED_PREFERENCES = "application"
private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()


        val mainContinueButton: Button = findViewById(R.id.continue_button)
        val mainYourNameTextView: EditText = findViewById(R.id.enter_your_name_textView)


        mainYourNameTextView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                mainContinueButton.isEnabled = s.toString().isNotBlank()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        mainContinueButton.setOnClickListener {
            saveUser(mainYourNameTextView.text.toString())
            navigateToHelloScreen()
        }
    }

    private fun navigateToHelloScreen() {
        val helloScreenIntent = Intent(this, HelloActivity::class.java)
        startActivity(helloScreenIntent)
    }

    private fun saveUser(userName: String) {
        val sharedPreferences: SharedPreferences = getSharedPreferences(APPLICATION_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        editor.putString(USER_NAME_KEY, userName)
        editor.apply()
    }

}