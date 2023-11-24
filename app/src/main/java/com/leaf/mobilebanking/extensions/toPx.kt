package com.leaf.mobilebanking.extensions

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

val Number.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )

fun String.toPhone(): String {
    var s = "+998"
    for (i in this.split(" "))
        s += i
    return s
}