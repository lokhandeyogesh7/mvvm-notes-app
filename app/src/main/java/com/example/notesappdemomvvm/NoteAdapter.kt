package com.example.notesappdemomvvm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NotHolder>() {

    var arrayList = ArrayList<Note>()
        lateinit var listener: onItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)

        return NotHolder(itemView)
    }

    override fun getItemCount(): Int {
        println("size of list is " + arrayList.size)
        return arrayList.size
    }

    override fun onBindViewHolder(holder: NotHolder, position: Int) {
        val itemNote = arrayList[position]
        holder.title.text = itemNote!!.title
        holder.priority.text = itemNote!!.priority.toString()
        holder.description.text = itemNote!!.description

        holder.itemView.setOnClickListener {
            val position1 = holder.adapterPosition
            if (position1 != RecyclerView.NO_POSITION) {
                listener.onItemClick(arrayList[position])
            }
        }
    }

    fun setNotes(notes: List<Note>?) {
        arrayList = (notes as ArrayList<Note>?)!!
        notifyDataSetChanged()
    }

    fun getNoteAt(position: Int): Note {
        return arrayList[position]
    }

    class NotHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.tvtitle)
        var priority: TextView = itemView.findViewById(R.id.tvPriority)
        var description: TextView = itemView.findViewById(R.id.tvdescription)
    }

    interface onItemClickListener {
        fun onItemClick(note: Note)
    }


    fun setOnItemClickListener(onclick: onItemClickListener) {
        listener = onclick
    }
}