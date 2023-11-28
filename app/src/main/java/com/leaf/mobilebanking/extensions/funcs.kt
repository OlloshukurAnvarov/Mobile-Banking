package com.leaf.mobilebanking.extensions

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import com.redmadrobot.inputmask.MaskedTextChangedListener
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

fun formatter(structure: String, editText: EditText) {
    val listener = MaskedTextChangedListener(structure, editText)
    editText.addTextChangedListener(listener)
    editText.onFocusChangeListener = listener
}

fun vibrateMe(context: Context) {
    val vibrator = context.getSystemService(AppCompatActivity.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(75, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        //deprecated in API 26
        vibrator.vibrate(75)
    }
}