package com.example.act01

import android.os.Bundle
import android.service.controls.templates.ThumbnailTemplate
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.act01.models.Movie

class DetailActivity : AppCompatActivity() {
   private lateinit var title : TextView
    private lateinit var any : TextView
    private lateinit var puntuacio : TextView
    private lateinit var thumbnailImage : ImageView
    private lateinit var saveButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        title = findViewById(R.id.titol)
        any = findViewById(R.id.any)
        puntuacio = findViewById(R.id.puntuacio)
        thumbnailImage = findViewById(R.id.thumb)
        saveButton = findViewById(R.id.guardar)
        val movieTitle = intent.getStringExtra("MOVIE_TITLE")
        val movieAny = intent.getStringExtra("MOVIE_DATA")
        val moviePuntuacio = intent.getDoubleExtra("MOVIE_SCORE", 0.0)
        val movieImage = intent.getStringExtra("MOVIE_IMAGE_URL")


        title.text = movieTitle
        any.text = "Any: $movieAny"
        puntuacio.text = "$moviePuntuacio"

        Glide.with(this)
            .load(movieImage)
            .into(thumbnailImage)


        saveButton.setOnClickListener {
           finish()
        }
    }

    }