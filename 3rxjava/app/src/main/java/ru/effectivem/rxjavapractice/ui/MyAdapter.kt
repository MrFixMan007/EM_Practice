package ru.effectivem.rxjavapractice.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.subjects.PublishSubject

class MyAdapter : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private val items = List(20) { "Item $it" }
    val clickSubject = PublishSubject.create<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(android.R.id.text1)

        fun bind(text: String, position: Int) {
            textView.text = text
            itemView.setOnClickListener {
                clickSubject.onNext(position)
            }
        }
    }
}
