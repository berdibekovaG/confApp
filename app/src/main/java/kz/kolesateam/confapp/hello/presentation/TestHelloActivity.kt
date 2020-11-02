package kz.kolesateam.confapp.hello.presentation

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import kz.kolesateam.confapp.R


class TestHelloActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_hello)
        val sharedPreferences: SharedPreferences = getSharedPreferences(
                APPLICATION_SHARED_PREFERENCES,
                Context.MODE_PRIVATE
        )

        val textInput: TextView  = findViewById(R.id.textView)
       val userName = sharedPreferences.getString(USER_NAME_KEY, "пользователь")
        println(userName)
   textInput.text= resources.getString(R.string.hello_user_fmt, userName)
    }


}