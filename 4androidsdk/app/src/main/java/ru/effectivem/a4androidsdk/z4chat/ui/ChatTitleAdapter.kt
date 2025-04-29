package ru.effectivem.a4androidsdk.z4chat.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.effectivem.a4androidsdk.R
import ru.effectivem.a4androidsdk.databinding.ChatTitleItemBinding
import ru.effectivem.a4androidsdk.z4chat.ui.base.BaseAdapter
import ru.effectivem.a4androidsdk.z4chat.ui.base.BaseViewHolder
import ru.effectivem.a4androidsdk.z4chat.ui.model.ChatTitle

class ChatTitleAdapter : BaseAdapter<ChatTitle>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ChatTitle> {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.chat_title_item, parent, false)
        )
    }

    class ViewHolder(itemView: View) :
        BaseViewHolder<ChatTitle>(itemView) {
        private val binding = ChatTitleItemBinding.bind(itemView)

        override fun bind(model: ChatTitle) {
            with(binding) {
                title.text = model.title
                lastMessage.text = model.lastMessage
            }
        }
    }
}