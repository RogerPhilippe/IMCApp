package br.com.fiap.rpp.imcapp.extensions

import android.widget.EditText

fun EditText.value() = this.text.toString()
fun Double.format(digits: Int) = "%.${digits}f".format(this)