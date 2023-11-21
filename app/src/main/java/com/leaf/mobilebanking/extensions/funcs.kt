package com.leaf.mobilebanking.extensions

import android.widget.EditText
import com.redmadrobot.inputmask.MaskedTextChangedListener

fun formatter(structure: String, editText: EditText) {
    val listener = MaskedTextChangedListener(structure, editText)
    editText.addTextChangedListener(listener)
    editText.onFocusChangeListener = listener
}