package com.xkf.ppjoke.base

import android.content.res.Resources
import kotlin.math.floor

object Utils {
    fun dpToPx(dpValue: Int): Int {
        return floor(Resources.getSystem().displayMetrics.density * dpValue.toDouble()).toInt()
    }
}