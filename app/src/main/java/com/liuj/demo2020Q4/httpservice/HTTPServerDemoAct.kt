package com.liuj.demo2020Q4.httpservice

import android.os.Bundle
import com.liuj.demo2020Q4.BaseAct
import com.liuj.demo2020Q4.R
import kotlinx.android.synthetic.main.socket_service_act.*

/**
 * @author liuj
 * @time 2020/12/31
 */
class HTTPServerDemoAct : BaseAct() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.socket_service_act)
        btn_start.setOnClickListener {
            startService()
        }
        btn_stop.setOnClickListener{
            stopService()
        }
    }

}