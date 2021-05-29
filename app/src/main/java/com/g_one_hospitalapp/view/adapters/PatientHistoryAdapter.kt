package com.g_one_hospitalapp.view.adapters

import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.g_one_hospitalapp.R
import com.g_one_hospitalapp.api.responses.MessageResponse
import kotlinx.android.synthetic.main.chat_list.view.*

class PatientHistoryAdapter: RecyclerView.Adapter<PatientHistoryAdapter.ViewHolder>() {
    private var messages = ArrayList<MessageResponse>()

    fun setMessages(m: ArrayList<MessageResponse>) {
        messages.clear()
        messages.addAll(m)
        notifyDataSetChanged()
    }

    fun setMessage(message: MessageResponse) {
        messages.add(message)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(item: MessageResponse) {
            with(itemView) {
                title_chat.text = item.message
                time.text = item.creationTime
                if (!item.response.isNullOrEmpty()) {
                    text_respon.visibility = View.VISIBLE
                    value_respon.visibility = View.VISIBLE
                    value_respon.text = "${item.response}"
                }
                if (!item.result.isNullOrEmpty()) {
                    text_hasil.visibility = View.VISIBLE
                    value_hasil.visibility = View.VISIBLE
                    value_hasil.text = "${item.result}"
                }
                if (!item.condition.isNullOrEmpty()) {
                    text_kondisi.visibility = View.VISIBLE
                    value_kondisi.visibility = View.VISIBLE
                    value_kondisi.text = "${item.condition}"
                }
                if (!item.action.isNullOrEmpty()) {
                    text_aksi.visibility = View.VISIBLE
                    value_aksi.visibility = View.VISIBLE
                    value_aksi.text = "${item.action}"
                }
                if (!item.attachments.isNullOrEmpty()) {
                    cardView.visibility = View.VISIBLE
                    Glide.with(itemView.context)
                            .load(item.attachments)
                            .into(docPicture)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.chat_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount(): Int = messages.size
}