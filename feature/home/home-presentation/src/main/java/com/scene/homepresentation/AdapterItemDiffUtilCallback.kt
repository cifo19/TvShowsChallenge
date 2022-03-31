package com.scene.homepresentation

import androidx.recyclerview.widget.DiffUtil
import com.scene.util.AdapterItem

class AdapterItemDiffUtilCallback(
    private val oldList: List<AdapterItem>,
    private val newList: List<AdapterItem>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
