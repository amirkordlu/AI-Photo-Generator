package com.amk.negareh.model.repository.photoGenerator

import com.amk.negareh.model.data.PhotoGeneratorDallEResponse

interface PhotoGeneratorRepository {

    suspend fun photoGeneratorDallE(
        model: String,
        promt: String,
        n: Int,
        size: String
    ): PhotoGeneratorDallEResponse

}