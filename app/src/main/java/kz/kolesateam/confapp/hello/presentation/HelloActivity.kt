package kz.kolesateam.confapp.hello.presentation


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import kz.kolesateam.confapp.ConfAppApplication
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.di.MEMORY_DATA_SOURCE
import kz.kolesateam.confapp.events.data.dataSource.UserNameDataSource
import kz.kolesateam.confapp.events.presentation.UpcomingEventsActivity
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named

private const val TAG = "HelloActivity"
const val APPLICATION_SHARED_PREFERENCES = "Name"

class HelloActivity : AppCompatActivity() {

    private val userNameDataSource: UserNameDataSource by inject(named(MEMORY_DATA_SOURCE))

    private lateinit var nameEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)

        nameEditText = findViewById(R.id.activity_main_name_edit_text)
        val findHalloButton = findViewById<Button>(R.id.activity_main_continue_button)

        findHalloButton.setOnClickListener {
            if (nameEditText.text.isNotBlank()) {
                saveUserName(nameEditText.text.toString().trim())
                startingTestActivity()
            }
        }

        nameEditText.addTextChangedListener { text ->
            if (text != null) {
                findHalloButton.isEnabled = text.isNotBlank()
            }
        }

    }

    private fun saveUserName(name: String) {
        userNameDataSource.saveUserName(name)
    }

    private fun startingTestActivity() {
        val intent = Intent(this, UpcomingEventsActivity::class.java)
        startActivity(intent)
    }
}