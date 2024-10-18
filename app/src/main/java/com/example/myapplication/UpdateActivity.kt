package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog

class UpdateActivity : AppCompatActivity() {

    private lateinit var exnameInput: EditText
    private lateinit var extypeInput: EditText
    private lateinit var durationInput: EditText
    private lateinit var updateButton: Button
    private lateinit var deleteButton: Button

    private var id: String? = null
    private var title: String? = null
    private var exTYPE: String? = null
    private var pages: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        exnameInput = findViewById(R.id.exname_input2)
        extypeInput = findViewById(R.id.extype_input2)
        durationInput = findViewById(R.id.duration_input2)
        updateButton = findViewById(R.id.update_button)
        deleteButton = findViewById(R.id.delete_button)

        // First we call this
        getAndSetIntentData()

        // Set actionbar title after getAndSetIntentData method
        val ab: ActionBar? = supportActionBar
        ab?.title = title

        updateButton.setOnClickListener {
            // And only then we call this
            val myDB = MyDatabaseHelper(this@UpdateActivity)
            title = exnameInput.text.toString().trim()
            exTYPE = extypeInput.text.toString().trim()
            pages = durationInput.text.toString().trim()
            myDB.updateData(id!!, title!!, exTYPE!!, pages!!)

            // Navigate to MainActivity
            val intent = Intent(this@UpdateActivity, MainActivity::class.java)
            startActivity(intent)
        }

        deleteButton.setOnClickListener {
            confirmDialog()
        }
    }

    private fun getAndSetIntentData() {
        if (intent.hasExtra("id") && intent.hasExtra("title") &&
            intent.hasExtra("exTYPE") && intent.hasExtra("pages")) {

            // Getting Data from Intent
            id = intent.getStringExtra("id")
            title = intent.getStringExtra("title")
            exTYPE = intent.getStringExtra("exTYPE")
            pages = intent.getStringExtra("pages")

            // Setting Intent Data
            exnameInput.setText(title)
            extypeInput.setText(exTYPE)
            durationInput.setText(pages)

            Log.d("step", "$title $exTYPE $pages")
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun confirmDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete $title ?")
        builder.setMessage("Are you sure you want to delete $title ?")
        builder.setPositiveButton("Yes") { _, _ ->
            val myDB = MyDatabaseHelper(this@UpdateActivity)
            myDB.deleteOneRow(id!!)
            finish()
        }
        builder.setNegativeButton("No", null)
        builder.create().show()
    }
}
