package com.zapato.zapato

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ToggleButton
import kotlinx.android.synthetic.main.activity_filter.*


class Filter : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        setTitle("Filters");

        val toggle = Male as ToggleButton
        toggle.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // The toggle is enabled
            } else {
                // The toggle is disabled
            }
        }

        val toggleFemale = Female as ToggleButton
        toggleFemale.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // The toggle is enabled
            } else {
                // The toggle is disabled
            }
        }
    }
}
