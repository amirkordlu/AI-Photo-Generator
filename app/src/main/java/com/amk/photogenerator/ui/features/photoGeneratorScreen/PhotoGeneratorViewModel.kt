package com.amk.photogenerator.ui.features.photoGeneratorScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amk.photogenerator.model.repository.photoGenerator.PhotoGeneratorRepository
import com.amk.photogenerator.util.PGDEX
import com.amk.photogenerator.util.coroutineExceptionHandler
import kotlinx.coroutines.launch

class PhotoGeneratorViewModel(
    private val photoGeneratorRepository: PhotoGeneratorRepository
) : ViewModel() {

    val generatedPhoto = mutableStateOf(PGDEX)

    val loading = mutableStateOf(false)

    fun photoGenerator(promt: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            loading.value = true
            val photo = photoGeneratorRepository.photoGeneratorDallE(
                "dall-e-2",
                promt,
                1,
                "1024x1024"
            )

            if (photo.created.toString().isNotEmpty()) {
                generatedPhoto.value = photo
                loading.value = false
            }

        }
    }
}