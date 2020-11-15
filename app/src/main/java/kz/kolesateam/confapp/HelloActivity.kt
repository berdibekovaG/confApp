package kz.kolesateam.confapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kz.kolesateam.confapp.events.presentation.UpcomingEventsActivity


class HelloActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)

        val mainContinueButton: Button = findViewById(R.id.continue_button)

        mainContinueButton.setOnClickListener {
            navigateToUpcomingEventsActivity()
        }
    }

    private fun navigateToUpcomingEventsActivity() {
        val helloScreenIntent = Intent(this, UpcomingEventsActivity::class.java)
        startActivity(helloScreenIntent)
    }

}