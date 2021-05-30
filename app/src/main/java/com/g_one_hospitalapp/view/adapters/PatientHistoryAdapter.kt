package com.g_one_hospitalapp.view.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.g_one_hospitalapp.MedRecordActivity
import com.g_one_hospitalapp.R
import com.g_one_hospitalapp.api.responses.ChatResponse
import kotlinx.android.synthetic.main.history_list.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PatientHistoryAdapter: RecyclerView.Adapter<PatientHistoryAdapter.ViewHolder>() {
    private var chats = ArrayList<ChatResponse>()

    fun setChats(c: ArrayList<ChatResponse>) {
        chats.clear()
        chats.addAll(c)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(item: ChatResponse) {
            val splitDate = item.createdAt.split(".").toTypedArray()
            val date = LocalDateTime.parse(splitDate[0])
            val formatter = DateTimeFormatter.ofPattern("dd LLLL YYYY")
            with(itemView) {
                title_chat.text = formatter.format(date)
                text_gName.text = item.hospital.name
                setOnClickListener {
                    val intent = Intent(itemView.context, MedRecordActivity::class.java)
                    intent.putExtra(MedRecordActivity.CHAT_ROOM_ID, item.id)
                    intent.putExtra(MedRecordActivity.IS_FROM_HISTORY, true)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return PatientHistoryAdapter.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.history_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(chats[position])

    override fun getItemCount(): Int = chats.size
}