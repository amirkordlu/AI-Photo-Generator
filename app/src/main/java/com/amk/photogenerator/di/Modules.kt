package com.amk.photogenerator.di

import com.amk.photogenerator.model.net.createApiService
import com.amk.photogenerator.model.repository.photoGenerator.PhotoGeneratorRepository
import com.amk.photogenerator.model.repository.photoGenerator.PhotoGeneratorRepositoryImpl
import com.amk.photogenerator.ui.features.photoGeneratorScreen.PhotoGeneratorViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModules = module {
    single { createApiService() }
    single<PhotoGeneratorRepository> { PhotoGeneratorRepositoryImpl(get()) }

    viewModel { PhotoGeneratorViewModel(get()) }

}