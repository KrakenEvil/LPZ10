package com.example.notepad

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

class Activity1 : AppCompatActivity() {
    var colors = arrayOf("#C5CAE9", "#B2EBF2", "#FFF9C4", "#425e17", "#587246")
    var sheetNumber = 0
    lateinit var text: EditText // Declare EditText without initializing it here

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1)

        text = findViewById(R.id.text) // Initialize text in onCreate

        sheetNumber = intent.getIntExtra("sheetNumber", 0)

        val next: Button = findViewById(R.id.next)
        next.setOnClickListener {
            if (sheetNumber < colors.size - 1) {
                val intent = Intent(this, this::class.java)
                intent.putExtra("sheetNumber", sheetNumber + 1)
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, getString(R.string.text2), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        val prefs = getPreferences(Context.MODE_PRIVATE).edit()
        prefs.putString("note$sheetNumber", text.editableText.toString())
        prefs.apply()
    }

    override fun onResume() {
        super.onResume()
        val sheet: ConstraintLayout = findViewById(R.id.sheet)
        sheet.setBackgroundColor(Color.parseColor(colors[sheetNumber]))
        val saveState = getPreferences(Context.MODE_PRIVATE).getString("note$sheetNumber", null)
        saveState?.let {
            text.setText(it)
        }
    }
}
