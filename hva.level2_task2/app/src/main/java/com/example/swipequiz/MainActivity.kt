package com.example.swipequiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val question = arrayListOf<Question>()
    private val questionAdapter = QuestionAdapter(question)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }


    private fun initViews() {

        // Initialize the recycler view with a linear layout manager, adapter
        rvQuestion.layoutManager =
            LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        rvQuestion.adapter = questionAdapter
        rvQuestion.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
                DividerItemDecoration.VERTICAL
            )
        )
        createItemTouchHelper().attachToRecyclerView(rvQuestion)


        for (i in Question.QUESTION_QUESTION.indices) {
            question.add(Question(Question.QUESTION_QUESTION[i], Question.QUESTION_ANWSER[i]))
        }
        questionAdapter.notifyDataSetChanged()

    }

    fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
//                question.removeAt(position)
                var answer = question.get(position).awnser
                questionAdapter.notifyItemChanged(viewHolder.adapterPosition)
                if (direction == 4 && answer == true || direction == 8 && answer == false) {
                    Snackbar.make(rvQuestion, getString(R.string.good) , Snackbar.LENGTH_SHORT)
                        .show()
                }else{Snackbar.make(rvQuestion, getString(R.string.bad), Snackbar.LENGTH_SHORT)
                    .show()}
            }
        }
        return ItemTouchHelper(callback)
    }
}

