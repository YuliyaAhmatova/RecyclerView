@file:Suppress("NAME_SHADOWING", "NAME_SHADOWING")

package com.example.recyclerview

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ThirdActivity : AppCompatActivity() {

    var thing: Thing? = null

    private lateinit var layoutCL: ConstraintLayout

    private lateinit var toolbarTA: Toolbar
    private lateinit var displayNameTV: TextView
    private lateinit var displayImageViewIV: ImageView
    private lateinit var displayDescriptionTV: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_third)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutCL)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        layoutCL = findViewById(R.id.layoutCL)
        displayNameTV = findViewById(R.id.displayNameTV)
        displayImageViewIV = findViewById(R.id.displayImageViewIV)
        displayDescriptionTV = findViewById(R.id.displayDescriptionTV)
        toolbarTA = findViewById(R.id.toolbarTA)

        setSupportActionBar(toolbarTA)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbarTA.setNavigationOnClickListener {
            onBackPressed()
        }

        if (intent.hasExtra("thing")) {
            thing = intent.getSerializableExtra("thing") as Thing
        }
        if (thing != null) {
            displayNameTV.text = thing!!.name
            displayDescriptionTV.text = thing!!.description
            displayImageViewIV.setImageResource(thing!!.image)
        }

        layoutCL.setOnLongClickListener {
            val dialog = AlertDialog.Builder(this)
            val inflater = this.layoutInflater
            val dialogView = inflater.inflate(R.layout.update_dialog, null)
            dialog.setView(dialogView)
            val nameET = dialogView.findViewById<EditText>(R.id.nameET)
            val descriptionET = dialogView.findViewById<EditText>(R.id.descriptionET)

            dialog.setTitle("Обновить запись")
            dialog.setMessage("Введите данные ниже")
            dialog.setPositiveButton("Обновить") { _, _ ->
                displayNameTV.text = nameET.text.toString()
                displayDescriptionTV.text = descriptionET.text.toString()
                val updateThing = Thing(
                    displayNameTV.text.toString(),
                    displayDescriptionTV.text.toString(),
                    thing?.image!!
                )
                val intent = Intent(this@ThirdActivity, SecondActivity::class.java)
                intent.putExtra("updatedThing", updateThing)
                setResult(Activity.RESULT_OK, intent)
            }
            dialog.setNegativeButton("Отмена") { _, _ ->
            }
            dialog.create().show()
            false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_ta, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.exitTAMenu -> {
                finishAffinity()
                Toast.makeText(applicationContext, "Программа завершена", Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}