package com.example.mvvmcoroutinesroomhiltsample.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmcoroutinesroomhiltsample.R
import com.example.mvvmcoroutinesroomhiltsample.data.local.entity.Note

class NotesAdapter(private val dataSet: ArrayList<Note>) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.tvDesc)
        val icon: ImageView = view.findViewById(R.id.ivIcon)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_layout_note, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val note = dataSet[position]

        viewHolder.textView.text = note.desc
        Glide.with(viewHolder.icon.context).load(note.icon).into(viewHolder.icon)
    }

    override fun getItemCount() = dataSet.size

    fun addData(listNotes: List<Note>) {
        dataSet.clear()
        dataSet.addAll(listNotes)
    }
}