package com.liuj.demo2020Q4.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.liuj.demo2020Q4.BaseRvHolder
import com.liuj.demo2020Q4.R

/**
 * @author liuj
 * @time 2021/1/10
 */

class NormalHolder(context: Context, itemView: View) : BaseRvHolder(context,itemView){
    companion object{
        fun newNormalHolder(context: Context, parents : ViewGroup) : NormalHolder {
            return NormalHolder(context, LayoutInflater.from(context).inflate(R.layout.rvholder_normal_holder, parents, false))
        }
    }

    override fun bindData(itemData: BaseRvModel, pos: Int) {
        itemView.findViewById<TextView>(R.id.tv).setText("position:${pos}")
    }

}