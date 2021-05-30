package com.g_one_hospitalapp.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.g_one_hospitalapp.R
import com.g_one_hospitalapp.api.responses.ChatResponse
import kotlinx.android.synthetic.main.history_list.view.*

class PatientHistoryAdapter: RecyclerView.Adapter<PatientHistoryAdapter.ViewHolder>() {
    private var chats = ArrayList<ChatResponse>()

    fun setChats(c: ArrayList<ChatResponse>) {
        chats.clear()
        chats.addAll(c)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(item: ChatResponse) {
            with(itemView) {
                title_chat.text = item.createdAt
                text_gName.text = item.hospital.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return PatientHistoryAdapter.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.history_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(chats[position])

    override fun getItemCount(): Int = chats.size
}