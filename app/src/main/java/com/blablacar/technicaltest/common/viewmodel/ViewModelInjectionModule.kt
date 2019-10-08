package com.blablacar.technicaltest.common.viewmodel

import androidx.lifecycle.ViewModelProvider
import com.blablacar.technicaltest.common.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module


@Module
abstract class ViewModelInjectionModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}