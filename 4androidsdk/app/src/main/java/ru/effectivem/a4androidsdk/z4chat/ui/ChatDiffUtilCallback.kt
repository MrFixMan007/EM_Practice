package ru.effectivem.a4androidsdk.z4chat.ui

import androidx.recyclerview.widget.DiffUtil
import ru.effectivem.a4androidsdk.z4chat.ui.model.ChatTitle

class ChatDiffUtilCallback(
    private val oldList: List<ChatTitle>,
    private val newList: List<ChatTitle>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}