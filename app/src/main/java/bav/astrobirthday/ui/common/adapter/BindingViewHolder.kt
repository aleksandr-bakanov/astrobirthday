package bav.astrobirthday.ui.common.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

open class BindingViewHolder<VB : ViewBinding>(
    protected val binding: VB
) : RecyclerView.ViewHolder(binding.root)