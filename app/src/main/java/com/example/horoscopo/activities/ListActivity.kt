package com.example.horoscopo.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.horoscopo.R
import com.example.horoscopo.adapters.HoroscopeAdapter
import com.example.horoscopo.data.Horoscope
import com.example.horoscopo.data.HoroscopeProvider


class ListActivity : AppCompatActivity() {

    // La lista de horoscopos a mostrar
    lateinit var horoscopeList: List<Horoscope>

    // La referencia del RecyclerView
    lateinit var recyclerView: RecyclerView

    // El adapter para decirle al RecyclerView que datos queremos listar y como
    lateinit var adapter: HoroscopeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_list)

        // Buscamos el RecyclerView en la vista
        recyclerView = findViewById(R.id.recyclerView)

        // Obtenemos el listado de horoscopos
        horoscopeList = HoroscopeProvider.findAll()

        // Creamos el adapter pasandole la lista de horoscopos y la función lambda para cuando se haga click en uno
        adapter = HoroscopeAdapter(horoscopeList) { position ->
            val horoscope = horoscopeList[position]
            navigateToDetail(horoscope)
        }

        // Asignamos el adapter al RecyclerView y le decimos que muestre las celdas verticalmente
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onResume() {
        super.onResume()

        // Refrescamos la lista notificando al adapter de que los datos han cambiado
        adapter.notifyDataSetChanged() // Esto lo hacemos para que refleje el favorito cuando cambie
    }

    // Función para mostrar el menú
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list_activity, menu)

        // Busco la opción de busqueda en el menú
        val searchMenuItem = menu?.findItem(R.id.menu_search)!!

        // Obtengo la clase del ActionView asociada a esa opción del menú
        val searchView = searchMenuItem.actionView as SearchView

        // Le asigno el listener a la busqueda
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            // función para cuando se pulsa enter
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            // función para cada vez que cambia el texto
            override fun onQueryTextChange(newText: String): Boolean {
                // Filtro la lista en base al nombre y las fechas del horóscopo
                horoscopeList = HoroscopeProvider.findAll().filter {
                    getString(it.name).contains(newText, true) ||
                    getString(it.dates).contains(newText, true)
                }

                // Le paso la nueva lista al adapter
                adapter.updateItems(horoscopeList)
                return true
            }
        })

        return true
    }

    // Navegar a DetailActivity pasandole el id del horóscopo seleccionado
    private fun navigateToDetail(horoscope: Horoscope) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("horoscope_id", horoscope.id)
        startActivity(intent)
    }
}