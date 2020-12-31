package com.liuj.demo2020Q4

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.liuj.demo2020Q4.base.adapter.BaseRvModel

/**
 * @author liuj
 * @time 2020/12/29
 */
open abstract class BaseRvHolder(val context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bindData(itemData: BaseRvModel, pos: Int)


}