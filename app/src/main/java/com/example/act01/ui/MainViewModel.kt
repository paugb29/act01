package com.example.act01.ui

import android.content.Context
import android.util.Log
import android.widget.EditText
import androidx.lifecycle.*
import com.example.act01.MainActivity
import com.google.gson.Gson
import com.example.act01.models.Movie
import com.example.act01.server.RetrofitConnection
import com.example.act01.server.RetrofitConnectionMovieDB
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    // Generamos liveData que son objetos que desde fuera se pueden
    // suscribir de tal manera que cuando el valor en el viewModel se modifique
    // el subscritor Observable se enterá y refrescarà al UI

    // Si estamos refrescando datos
    // 1. Una parte privada para uso interno el MutableLiveData permite asignar valores
    private val _loading = MutableLiveData(false)
    // 2. una parte pública para que desde fuera se puedan suscribir, el liveData solo de lectura
    public val loading: LiveData<Boolean> get() = _loading

    // El  número de películas
    private val _moviesCount = MutableLiveData(0)
    public val moviesCount: LiveData<Int> get() = _moviesCount

    // El número de favoritos
    private val _favoritosCount = MutableLiveData(0)
    public val favoritosCount: LiveData<Int> get() = _favoritosCount

    // El List de películas que devuelve la ApiRest
    private val _movies = MutableLiveData<List<Movie>>(emptyList())
    public val movies: LiveData<List<Movie>> get() = _movies

    // Error en la comunicación con la ApiRest
    private val _errorApiRest = MutableLiveData<String?>(null)
    public val errorApiRest: LiveData<String?> get() = _errorApiRest

    // Creamos la instancia de OpenAI
    //
    val MY_API_KEY = "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"
    val API_KEY = "Bearer $MY_API_KEY" // Replace MY_API_KEY with your own key and keep the word Bearer

    init {
        loadMovies()
    }

    public fun loadMovies() {
        // esto se ejecuta en el mismo instante que el viewModel se pone en marcha
        // sería como el crete de una activity.

        // cargamos las películas
        viewModelScope.launch {
            _loading.value = true
            _errorApiRest.value = null

            // Al executar aixo carregara totes les movies. P
            var response = RetrofitConnection.service.listMovies()

            if (response.isSuccessful) {
                _movies.value = response.body()
                _moviesCount.value = _movies.value!!.size
            }
            else {
                _errorApiRest.value = response.errorBody().toString()
            }

            _loading.value = false

        }
    }

    fun onMovieClicked(movie: Movie) {
        viewModelScope.launch {
            movie.favorite = movie.favorite != true
            RetrofitConnection.service.updateMovie(movie.id!!, movie)

            loadMovies()
            updateFavoritosCount()
        }
    }
     fun newMovie(textrebut: String, context: Context){
         viewModelScope.launch {
             val resposta = RetrofitConnectionMovieDB.service.searchMovies(textrebut)
             MaterialAlertDialogBuilder(context as MainActivity)
                 .setTitle("resultat peticio")
                 .setCancelable(false)
                 .setMessage(resposta.body().toString())
                 .setPositiveButton("Ok", null)
                 .show()
         }
    }
    private fun updateFavoritosCount() {
        val favoritesCount = _movies.value?.count { it.favorite } ?: 0

        _favoritosCount.value = favoritesCount
    }

     fun loadFavoriteMovies() {
        viewModelScope.launch {
            _loading.value = true
            _errorApiRest.value = null

            try {
                val response = RetrofitConnection.service.listMovies()
                if (response.isSuccessful) {
                    val movies = response.body()
                    movies?.let {
                        setFavoriteMovies(it)
                        _moviesCount.value = it.size
                    }
                } else {
                    _errorApiRest.value = "Error: ${response.code()} - ${response.message()}"
                }
            } catch (e: Exception) {
                _errorApiRest.value = "Error: ${e.message}"
            }

            _loading.value = false
        }
    }
    fun setFavoriteMovies(movies: List<Movie>) {
        _movies.value = movies.filter { it.favorite }
    }
    fun listMoviesAsc() {
        viewModelScope.launch {
            _loading.value = true
            _errorApiRest.value = null

            try {
                val response = RetrofitConnection.service.listMoviesAsc()
                if (response.isSuccessful) {
                    _movies.value = response.body()
                    _moviesCount.value = _movies.value?.size ?: 0
                } else {
                    _errorApiRest.value = response.errorBody()?.toString()
                }
            } catch (e: Exception) {
                _errorApiRest.value = "Error: ${e.message}"
            }

            _loading.value = false
        }
    }

    fun listMoviesDesc() {
        viewModelScope.launch {
            _loading.value = true
            _errorApiRest.value = null

            try {
                val response = RetrofitConnection.service.listMoviesDesc()

                if (response.isSuccessful) {
                    _movies.value = response.body()
                    _moviesCount.value = _movies.value?.size ?: 0
                } else {
                    _errorApiRest.value = response.errorBody()?.toString()
                }
            } catch (e: Exception) {
                _errorApiRest.value = "Error: ${e.message}"
            }

            _loading.value = false
        }
    }
    fun onMovieDelete(movie: Movie) {
        viewModelScope.launch {
            // actualizamos
            RetrofitConnection.service.deleteMovie(movie.id!!)

            // recargamos la lista que el observable de la activity recargará.
            loadMovies()
        }
    }

}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel() as T
    }
}