package kz.kolesateam.confapp.hello.presentation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaCodec.MetricsConstants.MODE
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import kz.kolesateam.confapp.APPLICATION_SHARED_PREFERENCES
import kz.kolesateam.confapp.MainActivity
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.USER_NAME_KEY

private const val TAG = "HelloActivity"

class HelloActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()


        val helloContinueButton: Button = findViewById(R.id.continue_button)
        val helloYourNameTextView: TextView = findViewById(R.id.hello_some_user_TextView)
        val user: String = getSavedUser()
        helloYourNameTextView.text = resources.getString(R.string.hello_some_user_fmt, user)

        helloContinueButton.setOnClickListener {
            finish()
        }
    }



    private fun getSavedUser(): String {
        val sharedPreferences: SharedPreferences = getSharedPreferences(APPLICATION_SHARED_PREFERENCES, Context.MODE_PRIVATE)

        return sharedPreferences.getString(USER_NAME_KEY, null) ?: "World"
    }
}