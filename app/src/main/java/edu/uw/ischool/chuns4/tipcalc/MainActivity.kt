package edu.uw.ischool.chuns4.tipcalc

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var inputAmount: EditText
    private lateinit var tipButton: Button
    private val tipPercentage: Int = 15

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inputAmount = findViewById(R.id.inputAmount)
        tipButton = findViewById(R.id.tipButton)
        tipButton.isEnabled = false
        inputAmount.addTextChangedListener {  }
        inputAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) {
                        val cleanString = s.toString().replace(Regex("[${'$'},.]"), "")
                        val parsed = cleanString.toDouble() / 100
                        val formatted = NumberFormat.getCurrencyInstance().format(parsed)
                        inputAmount.removeTextChangedListener(this)
                        inputAmount.setText(formatted)
                        inputAmount.setSelection(formatted.length)
                        inputAmount.addTextChangedListener(this)
                        tipButton.isEnabled = true
                } else {
                    tipButton.isEnabled = false
                }
            }
        })

        tipButton.setOnClickListener {
            calculateTip()
        }
    }

    private fun calculateTip() {
        val amountString = inputAmount.text.toString().replace(Regex("[$,]"), "")
        if (amountString.isNotEmpty()) {
            val amount = amountString.toDouble()
            val toPennies = (amount * 100).toInt()
            val tip = (toPennies * tipPercentage / 100)/ 100.0
            val dollarFormatted = NumberFormat.getCurrencyInstance().format(tip)
            Toast.makeText(this, " $dollarFormatted", Toast.LENGTH_SHORT).show()
        }
    }
}



