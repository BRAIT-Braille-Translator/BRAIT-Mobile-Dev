package com.bangkit.braitexample.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.bangkit.braitexample.Utils.uriToFile
import com.bangkit.braitexample.data.response.Result
import com.bangkit.braitexample.databinding.HomeScreenActivityBinding
import com.bangkit.braitexample.viewmodel.MainViewModel
import com.bangkit.braitexample.viewmodel.ViewModelFactory
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: HomeScreenActivityBinding
    private var currentImageUri: Uri? = null
    private var getFile: File? = null

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = HomeScreenActivityBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {

        binding.profileImage.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        binding.imageUploadIcon.setOnClickListener { startGallery() }

        binding.btTranslate.setOnClickListener { uploadImage() }
    }

    private fun uploadImage() {
        if (getFile != null) {
            val file = getFile as File
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "image", file.name, requestImageFile
            )
            viewModel.predictImage(imageMultipart).observe(this) { result ->
                if (result !=null) {
                    when (result) {
                        is Result.Loading -> {
                            binding.loadingProgressBar.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding.loadingProgressBar.visibility = View.GONE
                            val responseText = result.data.data.text
                            binding.tvResultTranslation.text = responseText
                            binding.tvResultTranslation.visibility = View.VISIBLE
                            Toast.makeText(
                                this,
                                "Predict Image ${result.data.message}",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                        is Result.Error -> {
                            binding.loadingProgressBar.visibility = View.GONE
                            Toast.makeText(this, "Predict Image ${result.error}", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.imageUploadIcon.setImageURI(it)
            Log.d("Image URI", "showImage: $it")

        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            val selectedImageFile: File = uriToFile(uri, this)
            getFile = selectedImageFile
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }
}