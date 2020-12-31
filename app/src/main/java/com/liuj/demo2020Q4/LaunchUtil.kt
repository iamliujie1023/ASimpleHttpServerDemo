package com.liuj.demo2020Q4

import android.content.Context
import android.content.Intent

/**
 * @author liuj
 * @time 2020/12/29
 */

fun launch(context: Context, clz: Class<*>) {
    context.startActivity(Intent(context, clz))
}