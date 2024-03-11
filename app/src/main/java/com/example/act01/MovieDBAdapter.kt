package com.example.act01

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.act01.R
import com.example.act01.models.Movie
import com.bumptech.glide.Glide

class MovieDBAdapter() : androidx.recyclerview.widget.ListAdapter<Movie, MovieDBAdapter.MovieViewHolder>(MovieDiffCallback()) {
    private var results: List<Movie> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titol)
        private val yearTextView: TextView = itemView.findViewById(R.id.any)
        private val scoreTextView: TextView = itemView.findViewById(R.id.puntuacio)
        private val thumbnailImageView: ImageView = itemView.findViewById(R.id.thumb)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)


        fun bind(movie: Movie) {
            titleTextView.text = movie.title
            yearTextView.text = movie.releaseDate
            scoreTextView.text = movie.voteAverage.toString()

            // Cargar la imagen del póster utilizando Glide
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${movie.backdropPath}")
                .into(thumbnailImageView)

        }
    }

    private class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
    fun setResults(results: List<Movie>) {
        this.results = results
        notifyDataSetChanged()
    }
}


