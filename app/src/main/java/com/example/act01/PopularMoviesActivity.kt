package com.example.act01.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.act01.MovieDBAdapter
import com.example.act01.MoviesAdapter
import com.example.act01.R
import com.example.act01.models.Movie
import com.example.act01.server.RetrofitConnectionMovieDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PopularMoviesActivity : AppCompatActivity() {



        //moviesDBAdapter = MovieDBAdapter()
        //        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        //        recyclerView.apply {
        //            layoutManager = LinearLayoutManager(this@PopularMoviesActivity)
        //            adapter = moviesDBAdapter
        //        }
        //
        //        var listadoPeliculas: List<Movie>
        //        GlobalScope.launch {
        //            val response = RetrofitConnectionMovieDB.service.getPopularMovies()
        //            listadoPeliculas = response.body()?.results ?: emptyList()
        //        }
        //        moviesDBAdapter.setResults(listadoPeliculas)
        //
        //
        //    }
    private lateinit var viewModel: PopularMoviesViewModel
    private lateinit var moviesDBAdapter: MovieDBAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(PopularMoviesViewModel::class.java)
        moviesDBAdapter = MovieDBAdapter()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@PopularMoviesActivity)
            adapter = moviesDBAdapter
        }

        viewModel.movies.observe(this, { movies ->
            moviesDBAdapter.submitList(movies)
        })

        loadMovies()
    }
    private fun loadMovies() {
        GlobalScope.launch(Dispatchers.IO) {
            viewModel.loadMovies()
        }
    }
}


