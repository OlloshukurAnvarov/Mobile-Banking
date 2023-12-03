package com.leaf.mobilebanking.extensions

import android.content.res.Resources
import android.util.TypedValue
import java.lang.StringBuilder
import kotlin.math.roundToInt
import kotlin.math.roundToLong

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

fun String.getLastFour(): String = "**** " + this.drop(this.length - 4)

fun validateDate(m: Int, y: Int): String = "$m/${y % 100}"

fun String.toAmount(): String = "$ " + String.format("%.2f", this.toDouble()).toDouble().withSpace()

fun Double.withSpace(): String {
    val s = StringBuilder()
    val f = this - this.toLong()

    this.toLong()
        .toString()
        .reversed()
        .forEachIndexed { index, digit ->
            if (index % 3 == 0 && index != 0)
                s.append(" ")
            s.append(digit)
        }
    return s.reversed().toString() + if (s.length <= 12) (f.toString().drop(1) + "0") else ""
}