package com.example.level3_task1

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_create_profile.*

const val GALLERY_REQUEST_CODE = 100

class CreateProfileActivity : AppCompatActivity() {
    private var profileImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)
        initViews()
    }

    private fun initViews() {

        btnGallery.setOnClickListener { onGalleryClick() }
        btnConfirm.setOnClickListener { onConfirmClick() }
    }

    private fun onConfirmClick() {
        val profile = Profile(
            etFirstName.text.toString(),
            etLastName.text.toString(),
            etProfileDescription.text.toString(),
            profileImageUri
        )

        val profileActivityIntent = Intent(this, ProfileActivity::class.java)
        profileActivityIntent.putExtra(PROFILE_EXTRA, profile)
        startActivity(profileActivityIntent)
    }


    private fun onGalleryClick() {
        // Create an Intent with action as ACTION_PICK
        val galleryIntent = Intent(Intent.ACTION_PICK)

        // Sets the type as image/*. This ensures only components of type image are selected
        galleryIntent.type = "image/*"

        // Start the activity using the gallery intent
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    profileImageUri = data?.data
                    ivProfileImage.setImageURI(profileImageUri)
                }
            }
        }
    }


}
