package com.liuj.demo2020Q4.base.adapter

import androidx.recyclerview.widget.RecyclerView
import com.liuj.demo2020Q4.BaseRvHolder

/**
 * @author liuj
 * @time 2020/12/29
 */
abstract class BaseRvAdapter : RecyclerView.Adapter<BaseRvHolder>(){
    private val mData = mutableListOf<BaseRvModel>()

    fun reset(data : List<BaseRvModel>){
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: BaseRvHolder, position: Int) {
        holder.bindData(mData[position], position)
    }



}