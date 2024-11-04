package com.example.horoscopo.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.horoscopo.R
import com.example.horoscopo.data.Horoscope
import com.example.horoscopo.data.HoroscopeProvider

class DetailActivity : AppCompatActivity() {

    lateinit var horoscope: Horoscope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)

        val id = intent.getStringExtra("horoscope_id")!!

        horoscope = HoroscopeProvider.findById(id)

        supportActionBar?.title = getString(horoscope.name)
        supportActionBar?.subtitle = getString(horoscope.dates)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        findViewById<TextView>(R.id.tv).setText(horoscope.name)
        findViewById<ImageView>(R.id.iv).setImageResource(horoscope.image)
        findViewById<Button>(R.id.b).setOnClickListener {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                println("Menu home")
                finish()
                return true
            }
            R.id.menu_favorite -> {
                println("Menu favorito")
                return true
            }
            R.id.menu_share -> {
                println("Menu compartir")
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onBackPressed() {
        if (horoscope.id == "aries") {
            Toast.makeText(this, "No puedes volver", Toast.LENGTH_LONG).show()
        } else {
            super.onBackPressed()
        }
    }
}