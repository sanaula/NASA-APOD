package com.example.wltest.home

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.wltest.R
import com.example.wltest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        viewModel.pictureLiveData.observe(this) {
            binding.apply {
                title.text = it?.title
                description.text = it?.explanation
                Glide.with(this@MainActivity).load(it?.url).into(picture)
            }
        }

        viewModel.fullPictureLiveData.observe(this) {
            Glide.with(this@MainActivity).load(it?.hdurl).into(binding.fullScreenPicture)
        }

        viewModel.errorLiveData.observe(this) {
            it?.let { showAlertDialog(it) }
        }
    }

    private fun showAlertDialog(title: String?) {
        val dialog = AlertDialog.Builder(this)
            .setTitle(title)
            .setPositiveButton(getString(R.string.ok)) { dialog, _ -> dialog.dismiss() }
            .create()
        dialog.show()
    }
}