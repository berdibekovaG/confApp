package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R

class HeadVIewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val userNameTextView = itemView.findViewById<TextView>(R.id.header_username_textview)

    fun onBind(userName: String) {
        userNameTextView.text =
                userNameTextView.resources.getString(R.string.hello_user_fmt, userName)
    }
}