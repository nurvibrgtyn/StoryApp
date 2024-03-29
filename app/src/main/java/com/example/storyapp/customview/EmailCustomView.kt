package com.example.storyapp.customview

import android.content.Context
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.addTextChangedListener
import com.example.storyapp.R

class EmailCustomView: AppCompatEditText {
    private var isEmailValid: Boolean = false

    constructor(context: Context): super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs){
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

        addTextChangedListener(onTextChanged = {p0: CharSequence?, p1: Int, p2: Int, p3: Int ->
            val email = text?.trim()
            if (email.isNullOrEmpty()) {
                isEmailValid = false
                error = resources.getString(R.string.input_email)
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                isEmailValid = false
                error = resources.getString(R.string.invalid_email)
            } else {
                isEmailValid = true
            }
        })
    }

}