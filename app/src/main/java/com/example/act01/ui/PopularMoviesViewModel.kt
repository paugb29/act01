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

class PopularMoviesViewModel: ViewModel() {
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

    // El List de películas que devuelve la ApiRest
    private val _movies = MutableLiveData<List<Movie>>(emptyList())
    public val movies: LiveData<List<Movie>> get() = _movies

    // Error en la comunicación con la ApiRest
    private val _errorApiRest = MutableLiveData<String?>(null)
    public val errorApiRest: LiveData<String?> get() = _errorApiRest

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
            var response = RetrofitConnectionMovieDB.service.getPopularMovies()

            if (response.isSuccessful) {
                _movies.value = response.body()?.results
                _moviesCount.value = _movies.value!!.size
            }
            else {
                _errorApiRest.value = response.errorBody().toString()
            }

            _loading.value = false

        }
    }

}

@Suppress("UNCHECKED_CAST")
class PopularMoviesViewModelFactory(): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return PopularMoviesViewModel() as T
    }
}