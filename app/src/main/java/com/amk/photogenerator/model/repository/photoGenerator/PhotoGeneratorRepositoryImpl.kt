package com.amk.photogenerator.model.repository.photoGenerator

import com.amk.photogenerator.model.data.PhotoGeneratorDallEResponse
import com.amk.photogenerator.model.net.ApiService
import com.google.gson.JsonObject

class PhotoGeneratorRepositoryImpl(
    private val apiService: ApiService
) : PhotoGeneratorRepository {

    override suspend fun photoGeneratorDallE(
        model: String,
        promt: String,
        n: Int,
        size: String
    ): PhotoGeneratorDallEResponse {

        val jsonObject = JsonObject().apply {
            addProperty("model", model)
            addProperty("prompt", promt)
            addProperty("n", n)
            addProperty("size", size)
        }
        val result = apiService.photoGeneratorDallE(jsonObject)
        return result
    }

}