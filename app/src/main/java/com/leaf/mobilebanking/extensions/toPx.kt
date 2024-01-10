package com.leaf.mobilebanking.extensions

import android.content.res.Resources
import android.util.TypedValue
import java.lang.StringBuilder
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

fun String.toPan(): String {
    var s = ""
    for (i in this.split(" "))
        s += i
    return s
}

fun String.toPhoneSpace(): String {
    val s = StringBuilder()
    this.forEachIndexed { index, digit ->
        if (index % 4 == 0 && index != 0)
            s.append(" ")
        s.append(digit)
    }
    return s.toString()
}

fun String.toMonth(): Int {
    return this.take(2).toInt()
}

fun String.toYear(): Int {
    return ("20" + this.takeLast(2)).toInt()
}

fun String.getLastFour(): String = "**** " + this.drop(this.length - 4)

fun validateDate(m: Int, y: Int): String = "$m/${y % 100}"

fun String.toAmount(): String {
    val v = this.replace(",", ".").removeRange(this.indexOf(".") + 3, this.length)
    return "$ " + String.format("%.2f", v.toDouble().roundToLong().toDouble()).replace(",", ".").toDouble()
        .withSpace()
}

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
    return s.reversed().toString() + if (s.length <= 9) (f.toString().drop(1) + "0") else ""
}