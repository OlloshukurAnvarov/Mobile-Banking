package com.leaf.mobilebanking.extensions

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import com.redmadrobot.inputmask.MaskedTextChangedListener
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

fun formatter(structure: String, editText: EditText) {
    val listener = MaskedTextChangedListener(structure, editText)
    editText.addTextChangedListener(listener)
    editText.onFocusChangeListener = listener
}

fun addMoneyFormatWatcher(editText: EditText) {
    editText.addTextChangedListener(object : TextWatcher {
        private val formatter: NumberFormat =
            DecimalFormat("#,###", DecimalFormatSymbols(Locale.getDefault()))
        private var current: String = ""

        override fun afterTextChanged(s: Editable?) {
            if (s.toString() != current) {
                editText.removeTextChangedListener(this)

                // Remove existing commas and spaces
                val cleanInput = s.toString().replace("[,\\s]".toRegex(), "")

                // Convert the cleaned input to a Long
                val amount = if (cleanInput.isNotEmpty()) cleanInput.toLong() else 0

                // Format the Long value with spaces
                val formattedMoney = formatter.format(amount).replace(",", " ")

                current = formattedMoney
                editText.setText(formattedMoney)
                editText.setSelection(formattedMoney.length) // Move cursor to the end

                editText.addTextChangedListener(this)
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // Not needed in this case
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // Not needed in this case
        }
    })
}

fun vibrateMe(context: Context) {
    val vibrator = context.getSystemService(AppCompatActivity.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        //deprecated in API 26
        vibrator.vibrate(100)
    }
}