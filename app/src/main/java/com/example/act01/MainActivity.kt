package com.example.act01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.act01.ui.MainViewModel
import com.example.act01.ui.PopularMoviesActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        moviesAdapter = MoviesAdapter { movie ->
            // Eliminar la película de la lista de favoritos
            viewModel.onMovieDelete(movie)
        }
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = moviesAdapter
        }

        viewModel.movies.observe(this, { movies ->
            moviesAdapter.submitList(movies)
        })

        loadFavoriteMovies()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.ordenarAsc -> {
                ordenarAscendente()
                return true
            }
            R.id.ordenarDesc -> {
                ordenarDescendante()
                return true
            }

            R.id.novaPeli -> {
                newMovie()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }

        return(super.onOptionsItemSelected(item));
    }

    private fun newMovie(){
       // var edtData: EditText = EditText(this)
        //        MaterialAlertDialogBuilder(this)
        //            .setTitle("nova pel·licula")
        //            .setCancelable(false)
        //            .setMessage("Introdueix el títol de la pel·licula")
        //            .setView(edtData)
        //            .setNegativeButton("Cancelar",null)
        //            .setPositiveButton("buscar"){
        //                dialog, which ->
        //                val textIntroduit = edtData.text
        //                viewModel.newMovie(textIntroduit.toString(),this)
        //            }
        //            .show()

        startActivity(Intent(this, PopularMoviesActivity::class.java))
    }





    private fun ordenarAscendente() {
        GlobalScope.launch(Dispatchers.IO) {
            viewModel.listMoviesAsc()
        }
    }
    private fun ordenarDescendante() {
        GlobalScope.launch(Dispatchers.IO) {
            viewModel.listMoviesDesc()
        }
    }
    private fun loadFavoriteMovies() {
        GlobalScope.launch(Dispatchers.IO) {
            viewModel.loadFavoriteMovies()
        }
    }

}

