package com.liuj.demo2020Q4.md.appbarlayout

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.liuj.demo2020Q4.BaseAct
import com.liuj.demo2020Q4.BaseRvHolder
import com.liuj.demo2020Q4.R
import com.liuj.demo2020Q4.base.adapter.BaseRvAdapter
import com.liuj.demo2020Q4.base.adapter.BaseRvModel
import com.liuj.demo2020Q4.base.adapter.NormalHolder
import com.liuj.demo2020Q4.httpservice.HTTPServerDemoAct
import com.liuj.demo2020Q4.md.appbarlayout.DemoAct
import kotlinx.android.synthetic.main.coordinator_demo1_act.*

/**
 * @author liuj
 * @time 2021/1/10
 */
class DemoAct : BaseAct() {

    private lateinit var mAdapter : MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coordinator_demo1_act)

        mAdapter = MyAdapter(this)
        rv?.adapter = mAdapter
        rv?.layoutManager = LinearLayoutManager(this)
        mAdapter.reset(data)


    }


}


class MyAdapter(val context : Context) : BaseRvAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRvHolder {
        return NormalHolder.newNormalHolder(context = context, parents = parent)
    }

}

val data = listOf(
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("HTTPService11", HTTPServerDemoAct::class.java)
)

