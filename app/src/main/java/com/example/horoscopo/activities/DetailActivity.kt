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
import com.example.horoscopo.utils.SessionManager


class DetailActivity : AppCompatActivity() {

    // El horóscopo que queremos visualizar
    lateinit var horoscope: Horoscope

    // Si el horóscopo es favorito o no
    var isFavorite = false

    // La opción del menu de favorito para poder cambiar el icono
    lateinit var favoriteMenuItem: MenuItem

    // El objeto que gestiona la sesión para poder guardar el favorito
    lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)

        // Obtenemos el parámetro del horóscopo que queremos visualziar
        val id = intent.getStringExtra("horoscope_id")!!

        // Buscamos el horóscopo
        horoscope = HoroscopeProvider.findById(id)

        // Modificamos el ActionBar para mostrar título y subtítulo
        supportActionBar?.title = getString(horoscope.name)
        supportActionBar?.subtitle = getString(horoscope.dates)
        // Habilitamos el boton de volver atras en el ActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Instanciamos el objeto de la sesión
        session = SessionManager(this)

        // Revisamos si el horóscopo es favorito
        isFavorite = session.isFavorite(horoscope.id)

        // Pruebas
        findViewById<TextView>(R.id.tv).setText(horoscope.name)
        findViewById<ImageView>(R.id.iv).setImageResource(horoscope.image)
        findViewById<Button>(R.id.b).setOnClickListener {
            finish()
        }
    }

    // Función para mostrar el menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_activity, menu)

        // Buscamos la opción del menu de favorito
        favoriteMenuItem = menu?.findItem(R.id.menu_favorite)!!

        // Cambiamos el icono en función de si es favorito o no
        setFavoriteIcon()

        return true
    }

    // Funcionar para capturar que opcion del menu se ha clickeado
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Opcion volver atras
                finish() // Cerramos el Activity
                return true
            }
            R.id.menu_favorite -> {
                if (isFavorite) {
                    session.setFavorite("")
                } else {
                    session.setFavorite(horoscope.id)
                }
                isFavorite = !isFavorite
                setFavoriteIcon()
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

    fun setFavoriteIcon() {
        if(isFavorite) {
            favoriteMenuItem.setIcon(R.drawable.ic_favorite_selected)
        } else {
            favoriteMenuItem.setIcon(R.drawable.ic_favorite)
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