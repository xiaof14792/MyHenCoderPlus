package com.example.core

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val viewHashMap = HashMap<Int, View>()

    fun <T : View>  getView(id: Int): T {
        var view = viewHashMap.get(id);
        if (view == null) {
            view = itemView.findViewById(id)
            viewHashMap.put(id, view)
        }
        return view as T
    }

    fun setText(id: Int, text: String ) {
        getView<TextView>(id).text = text
    }
}