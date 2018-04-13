package com.icoalert.ui.fragmenticoslist

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.icoalert.R
import com.icoalert.api.data.IcoDto
import com.icoalert.databinding.LayoutIcoItemBinding


class IcosListRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data = ArrayList<IcoDto>()
    private val VIEW_ITEM = R.layout.layout_ico_item
    private val VIEW_PROGRESS = R.layout.layout_ico_bottom_progress
    var withProgress = false

    init {
        setHasStableIds(true)
    }

    fun setData(dtoItems : ArrayList<IcoDto>) {
        data.clear()
        data.addAll(dtoItems)
        notifyDataSetChanged()
    }

    fun addData(dtoItems: java.util.ArrayList<IcoDto>) {
        data.addAll(dtoItems)
    }


    override fun getItemCount(): Int {
        return if (withProgress) data.size + 1 else data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val vh: RecyclerView.ViewHolder
        if (viewType == VIEW_ITEM) {
            val binding = DataBindingUtil.inflate<LayoutIcoItemBinding>(layoutInflater, viewType, parent, false)
            vh = IcoViewHolder(binding)
        } else {
            val v = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
            vh = ProgressViewHolder(v)
        }
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position< data.size) {
            (holder as IcoViewHolder).bind(data[position])
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        if (position<data.size) {
            return VIEW_ITEM
        } else {
            return VIEW_PROGRESS
        }
    }

    class IcoViewHolder(private val binding: LayoutIcoItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (dto: IcoDto) {
            binding.icoDto = dto
        }
    }

    class ProgressViewHolder(v: View) : RecyclerView.ViewHolder(v)
}