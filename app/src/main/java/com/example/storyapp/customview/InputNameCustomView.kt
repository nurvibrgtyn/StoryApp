package com.example.storyapp.customview

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.addTextChangedListener
import com.example.storyapp.R

class InputNameCustomView : AppCompatEditText {

    private var isNameValid: Boolean = false

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

        addTextChangedListener(onTextChanged = {p0: CharSequence?, p1: Int, p2: Int, p3: Int ->
            val name = text?.trim()
            if (name.isNullOrEmpty()) {
                isNameValid = false
                error = resources.getString(R.string.name_required)
            } else {
                isNameValid = true
            }
        })
    }
}