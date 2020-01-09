package com.example.level3_task2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_create_portal.*
import kotlinx.android.synthetic.main.item_portal.view.*

const val EXTRA_PORTAL = "extra_portal"

class CreatePortal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_portal)
        initview()
    }

    fun initview() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "This is your profile!"
        btnAdd.setOnClickListener { onAddClick() }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun onAddClick() {
        if (etTitle.text.toString().isNotBlank() && etUrl.text.toString().isNotBlank()) {
            //get the url and check the length of it        }
            var url = etUrl.text.toString()
            val length = url.length
            if (length > 4) {
                //check if the url starts with http else add http to the beginning
                val shortUrl = url.substring(0, 3)
                if (shortUrl != HTTP_STRING) {
                    url = "http::/$url"
                }
            } else {
                url = "http::/$url"
            }
            //save the portal en return
            val portal = Portal(etTitle.text.toString(), url)
            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_PORTAL, portal)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        } else {
            Toast.makeText(
                this, "The portal cannot be empty"
                , Toast.LENGTH_SHORT
            ).show()
        }
    }
}
