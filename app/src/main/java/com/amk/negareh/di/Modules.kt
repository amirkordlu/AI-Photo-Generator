package com.amk.negareh.di

import com.amk.negareh.model.net.createApiService
import com.amk.negareh.model.repository.photoGenerator.PhotoGeneratorRepository
import com.amk.negareh.model.repository.photoGenerator.PhotoGeneratorRepositoryImpl
import com.amk.negareh.ui.features.photoGeneratorScreen.PhotoGeneratorViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModules = module {
    single { createApiService() }
    single<PhotoGeneratorRepository> { PhotoGeneratorRepositoryImpl(get()) }

    viewModel { PhotoGeneratorViewModel(get()) }

}