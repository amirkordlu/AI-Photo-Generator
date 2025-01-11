package com.amk.negareh.model.data

data class PhotoGeneratorDallEResponse(
    val created: Int,
    val `data`: List<Data>
)

data class Data(
    val revised_prompt: String,
    val url: String
)