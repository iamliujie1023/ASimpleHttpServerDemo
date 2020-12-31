package com.liuj.demo2020Q4.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.liuj.demo2020Q4.BaseRvHolder
import com.liuj.demo2020Q4.R
import com.liuj.demo2020Q4.base.adapter.BaseRvAdapter
import com.liuj.demo2020Q4.base.adapter.BaseRvModel
import com.liuj.demo2020Q4.launch

/**
 * @author liuj
 * @time 2020/12/29
 */
class MainAdapter(val context: Context) : BaseRvAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRvHolder {
        return MainHolder(
            context,
            LayoutInflater.from(context).inflate(R.layout.rvholder_main_holder, parent, false)
        )
    }

}

class MainHolder(context: Context, itemView: View) : BaseRvHolder(context, itemView) {

    override fun bindData(data: BaseRvModel, pos: Int) {
        itemView.findViewById<TextView>(R.id.tv_1).text = data.title
        itemView.setOnClickListener{
            launch(context, data.clazz)
        }
    }


}

