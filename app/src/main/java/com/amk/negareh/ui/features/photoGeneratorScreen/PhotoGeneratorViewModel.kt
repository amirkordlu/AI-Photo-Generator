package com.amk.negareh.ui.features.photoGeneratorScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amk.negareh.model.repository.photoGenerator.PhotoGeneratorRepository
import com.amk.negareh.util.PGDEX
import com.amk.negareh.util.PhotoGeneratorModel
import com.amk.negareh.util.PhotoGeneratorN
import com.amk.negareh.util.PhotoGeneratorSize
import com.amk.negareh.util.coroutineExceptionHandler
import kotlinx.coroutines.launch

class PhotoGeneratorViewModel(
    private val photoGeneratorRepository: PhotoGeneratorRepository
) : ViewModel() {

    val generatedPhoto = mutableStateOf(PGDEX)

    val loading = mutableStateOf(false)

    fun photoGenerator(promt: String, onError: () -> Unit) {
        viewModelScope.launch(coroutineExceptionHandler) {
            try {
                loading.value = true
                val photo = photoGeneratorRepository.photoGeneratorDallE(
                    PhotoGeneratorModel,
                    promt,
                    PhotoGeneratorN,
                    PhotoGeneratorSize
                )

                if (
                    photo.created > 0 &&
                    photo.data.isNotEmpty() &&
                    photo.data[0].url.isNotBlank()
                ) {
                    generatedPhoto.value = photo
                    loading.value = false
                } else {
                    loading.value = true
                    onError.invoke()
                }
            } catch (e: Exception) {
                onError.invoke()
                e.localizedMessage?.let { Log.e("PhotoGeneratorViewModel", it) }
            }
        }
    }
}