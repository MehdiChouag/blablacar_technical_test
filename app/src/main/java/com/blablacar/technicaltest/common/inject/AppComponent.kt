package com.blablacar.technicaltest.common.inject

import android.content.Context
import com.blablacar.technicaltest.BlaBlaTripApplication
import com.blablacar.technicaltest.common.viewmodel.ViewModelInjectionModule
import com.blablacar.technicaltest.common.network.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * All app related module are defined here.
 */
@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, NetworkModule::class, ViewModelInjectionModule::class, ActivityBuilderModule::class])
interface AppComponent : AndroidInjector<BlaBlaTripApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance appContext: Context): AppComponent
    }
}