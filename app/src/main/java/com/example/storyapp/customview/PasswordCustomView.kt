package com.example.storyapp.customview

import android.content.Context
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.addTextChangedListener
import com.example.storyapp.R

class PasswordCustomView: AppCompatEditText {
    private var isPassValid: Boolean = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        transformationMethod = PasswordTransformationMethod.getInstance()

        addTextChangedListener(onTextChanged = {p0: CharSequence?, p1: Int, p2: Int, p3: Int ->
            val pass = text?.trim()
            when {
                pass.isNullOrEmpty() -> {
                    isPassValid = false
                    error = resources.getString(R.string.input_pass)
                }
                pass.length < 8 -> {
                    isPassValid = false
                    error = resources.getString(R.string.pass_length)
                }
                else -> {
                    isPassValid = true
                }
            }
        })
    }
}