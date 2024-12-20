package com.amk.photogenerator.model.repository.photoGenerator

import com.amk.photogenerator.model.data.PhotoGeneratorDallEResponse
import com.google.gson.JsonObject

interface PhotoGeneratorRepository {

    suspend fun photoGeneratorDallE(
        model: String,
        promt: String,
        n: Int,
        size: String
    ): PhotoGeneratorDallEResponse

}