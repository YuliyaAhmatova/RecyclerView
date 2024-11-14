package com.example.recyclerview

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SecondActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE = 100
    }

    var updateThing: Thing? = null
    private var adapter: CustomAdapter? = null
    private var things: MutableList<Thing> = ThingDataBase.things
    private val dataBase = DBHelper(this)

    private lateinit var toolbarSA: Toolbar
    private lateinit var recyclerViewRV: RecyclerView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbarSA = findViewById(R.id.toolbarSA)
        recyclerViewRV = findViewById(R.id.recyclerViewRV)
        viewDataAdapter()

        setSupportActionBar(toolbarSA)
        title = "Мой гардероб"

        recyclerViewRV.layoutManager = LinearLayoutManager(this)

        recyclerViewRV.setHasFixedSize(true)
        adapter?.setOnThingClickListener(object :
            CustomAdapter.OnThingClickListener {
            override fun onThingClick(thing: Thing, position: Int) {
                val intent = Intent(this@SecondActivity, ThirdActivity::class.java)
                intent.putExtra("thing", thing)
                startActivityForResult(intent, REQUEST_CODE)
            }
        })
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            val updatedThingTwo = data?.getSerializableExtra("updatedThing") as Thing
            updateThing = updatedThingTwo
            updateRecord()
        }
    }

    private fun updateRecord() {
        val newThing = updateThing
        if (newThing != null) {
            dataBase.updateThing(newThing)
            viewDataAdapter()
            updateThing = null
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun viewDataAdapter() {
        things = dataBase.readThing()
        adapter = CustomAdapter(things)
        recyclerViewRV.adapter = adapter
        adapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_sa, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.exitSAMenu -> {
                finishAffinity()
                Toast.makeText(applicationContext, "Программа завершена", Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}