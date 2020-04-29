package com.example.lab3machinelearningkit

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage

class MainActivity : AppCompatActivity()
{
    private val pickPhotoRequestCode = 101
    private lateinit var tagsTV: TextView
    private lateinit var contentIV: ImageView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tagsTV = findViewById(R.id.labelPreview)
        contentIV = findViewById(R.id.uploadedImagePreview)

        val button: Button = findViewById(R.id.buttonSelectImage)
        button.setOnClickListener {
            pickImage()
        }

        val button2 = findViewById<Button>(R.id.buttonSwitchText)
        button2.setOnClickListener{
            val intent = Intent(this, TextRecActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int,
                                  resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                pickPhotoRequestCode -> {
                    val bitmap = getImageFromData(data)
                    bitmap?.apply {
                        contentIV.setImageBitmap(this)
                        processImageTagging(bitmap)
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode,
            data)
    }

    private fun processImageTagging(bitmap: Bitmap) {
        val visionImg =
            FirebaseVisionImage.fromBitmap(bitmap)
        val labeler =
            FirebaseVision.getInstance().getOnDeviceImageLabeler().
            processImage(visionImg)
                .addOnSuccessListener { tags ->
                    tagsTV.text = tags.joinToString(" ") {
                        it.text }
                }
                .addOnFailureListener { ex ->
                    Log.wtf("LAB", ex)
                }
    }

    private fun getImageFromData(data: Intent?): Bitmap? {
        val selectedImage = data?.data
        return MediaStore.Images.Media.getBitmap(this.contentResolver,
            selectedImage)
    }

    private fun pickImage()
    {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, pickPhotoRequestCode)
    }
}
