package com.liuj.demo2020Q4.main

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.liuj.demo2020Q4.BaseAct
import com.liuj.demo2020Q4.R
import com.liuj.demo2020Q4.base.adapter.BaseRvModel
import com.liuj.demo2020Q4.httpserver2.SimpleAct
import com.liuj.demo2020Q4.httpservice.HTTPServerDemoAct
import com.liuj.demo2020Q4.socketservice.SocketServiceTestAct

/**
 * @author liuj
 * @time 2020/12/29
 */
class MainAct : BaseAct() {

    private val mAdapter = MainAdapter(this)
    private var rv : RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_act)
        rv = findViewById(R.id.main_rv)
        rv?.adapter = mAdapter
        rv?.layoutManager = LinearLayoutManager(this)
        mAdapter.reset(data)
    }

}

val data = listOf(
    BaseRvModel("socketService", SocketServiceTestAct::class.java),
    BaseRvModel("HTTPService", HTTPServerDemoAct::class.java),
    BaseRvModel("SimpleHTTPService", SimpleAct::class.java)

)