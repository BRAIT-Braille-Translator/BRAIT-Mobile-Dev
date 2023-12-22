package com.bangkit.braitexample.viewmodel

import androidx.lifecycle.ViewModel
import com.bangkit.braitexample.repository.PredictRepository
import okhttp3.MultipartBody


class MainViewModel(private val predictRepository: PredictRepository): ViewModel() {

    fun predictImage(
        file: MultipartBody.Part
    ) = predictRepository.predictImage(file)
}