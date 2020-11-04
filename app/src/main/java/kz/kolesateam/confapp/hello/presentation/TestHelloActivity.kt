package kz.kolesateam.confapp.hello.presentation

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kz.kolesateam.confapp.R

        const val DEFAULT_USER_NAME = "пользователь"
class TestHelloActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_hello)
        val sharedPreferences: SharedPreferences = getSharedPreferences(
                APPLICATION_SHARED_PREFERENCES,
                Context.MODE_PRIVATE
        )

        val helloTextView: TextView  = findViewById(R.id.textview_test_hello)
        val userName = sharedPreferences.getString(USER_NAME_KEY, DEFAULT_USER_NAME)
        helloTextView.text= resources.getString(R.string.hello_user_fmt, userName)
    }


}