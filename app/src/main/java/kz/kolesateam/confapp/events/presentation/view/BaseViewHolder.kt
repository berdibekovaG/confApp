package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(item: View) :RecyclerView.ViewHolder(item) {
    abstract fun onBind(data: T)
}