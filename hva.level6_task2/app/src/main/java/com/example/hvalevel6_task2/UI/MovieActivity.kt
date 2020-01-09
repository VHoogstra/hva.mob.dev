package com.example.hvalevel6_task2.UI

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.hvalevel6_task2.Model.Movie
import com.example.hvalevel6_task2.R
import kotlinx.android.synthetic.main.activity_movie.*

const val MOVIE = "MOVIE"

class MovieActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViews()
    }

    private fun initViews(){
        val movie = intent.getParcelableExtra<Movie>(MOVIE)
        Glide.with(this).load(movie?.getBackdropUrl()).into(ivBackdrop)
        Glide.with(this).load(movie?.getPosterUrl()).into(ivPoster)
        tvTitle.text = movie?.title
        tvRelease.text = getString(R.string.release,movie?.releaseDate)
        tvRating.text = movie?.rating.toString()
        tvOverview.text = movie?.overview
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
