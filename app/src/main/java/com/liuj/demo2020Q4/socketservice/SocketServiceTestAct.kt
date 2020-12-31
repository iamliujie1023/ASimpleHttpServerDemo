package com.liuj.demo2020Q4.socketservice

import android.os.Bundle
import com.liuj.demo2020Q4.BaseAct
import com.liuj.demo2020Q4.R.*
import kotlinx.android.synthetic.main.socket_service_act.*

/**
 * @author liuj
 * @time 2020/12/29
 */
class SocketServiceTestAct : BaseAct(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.socket_service_act)
        btn_start.setOnClickListener {
            startService()
        }
        btn_stop.setOnClickListener{
            stopService()
        }
    }

}