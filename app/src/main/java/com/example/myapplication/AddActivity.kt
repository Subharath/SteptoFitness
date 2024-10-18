package com.example.myapplication

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AddActivity : AppCompatActivity() {

    private lateinit var exnameInput: EditText
    private lateinit var extypeInput: EditText
    private lateinit var durationInput: EditText
    private lateinit var addButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        exnameInput = findViewById(R.id.exname_input)
        extypeInput = findViewById(R.id.extype_input)
        durationInput = findViewById(R.id.duration_input)
        addButton = findViewById(R.id.add_button)

        addButton.setOnClickListener {
            val title = exnameInput.text.toString().trim()
            val exTYPE = extypeInput.text.toString().trim()
            val pages = durationInput.text.toString().trim()

            // Validation
            if (title.isEmpty()) {
                exnameInput.error = "Exercise Name is required"
                return@setOnClickListener
            }

            if (exTYPE.isEmpty()) {
                extypeInput.error = "Exercise Type is required"
                return@setOnClickListener
            }

            if (pages.isEmpty()) {
                durationInput.error = "Duration is required"
                return@setOnClickListener
            }

            try {
                val pagesInt = pages.toInt()
                if (pagesInt <= 0) {
                    durationInput.error = "Enter a Duration"
                    return@setOnClickListener
                }

                // Add the book to the database
                val myDB = MyDatabaseHelper(this@AddActivity)
                myDB.addBook(title, exTYPE, pagesInt)


                // Show success message
                Toast.makeText(this@AddActivity, "Exercise added successfully!", Toast.LENGTH_SHORT).show()

                // Clear inputs after adding
                exnameInput.setText("")
                extypeInput.setText("")
                durationInput.setText("")

                // Navigate to MainActivity
                val intent = Intent(this@AddActivity, MainActivity::class.java)
                startActivity(intent)

            } catch (e: NumberFormatException) {
                durationInput.error = "Enter a valid Duration"
            }
        }
    }
}
