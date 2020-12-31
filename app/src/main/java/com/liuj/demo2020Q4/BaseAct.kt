package com.liuj.demo2020Q4

import android.os.Bundle
import androidx.fragment.app.FragmentActivity

/**
 * @author liuj
 * @time 2020/12/29
 */
open class BaseAct : FragmentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = this.javaClass.simpleName
    }

}