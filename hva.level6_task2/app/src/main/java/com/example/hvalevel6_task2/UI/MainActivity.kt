package com.example.hvalevel6_task2.UI

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hvalevel6_task2.MainActivityViewModel
import com.example.hvalevel6_task2.Model.Movie
import com.example.hvalevel6_task2.MovieAdapter
import com.example.hvalevel6_task2.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val movies = arrayListOf<Movie>()
    private lateinit var viewModel: MainActivityViewModel
    private val movieSelectAdapter =
        MovieAdapter(movies) { movie -> onMovieClick(movie) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initViewModel()

    }

    private fun initView() {
        val gridLayoutManager = GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)
        rvMovies.layoutManager = gridLayoutManager
        rvMovies.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
            override fun onGlobalLayout() {
                rvMovies.viewTreeObserver.removeOnGlobalLayoutListener(this)
                gridLayoutManager.spanCount = calculateSpanCount()
                gridLayoutManager.requestLayout()
            }
        })

        btnSubmit.setOnClickListener { onSubmitClick() }

        rvMovies.adapter = movieSelectAdapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewModel.movies.observe(this, Observer {
            movies.clear()
            movies.addAll(it)
            movieSelectAdapter.notifyDataSetChanged()
        })
    }

    private fun onSubmitClick() {
        viewModel.getMoviesForYear(etYear.text.toString())
    }

    private fun onMovieClick(movie: Movie) {
        val intent = Intent(this, MovieActivity::class.java)
        intent.putExtra("MOVIE", movie)
        startActivity(intent)
    }

    private fun calculateSpanCount(): Int {
        val viewWidth = rvMovies.measuredWidth
        val cardViewWidth = resources.getDimension(R.dimen.poster_width)
        val cardViewMargin = resources.getDimension(R.dimen.margin_medium)
        val spanCount = StrictMath.floor((viewWidth / (cardViewWidth + cardViewMargin)).toDouble()).toInt()
        return if (spanCount >= 1) spanCount else 1
    }
}
