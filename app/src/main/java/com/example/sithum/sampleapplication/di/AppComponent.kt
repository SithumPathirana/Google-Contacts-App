package com.example.sithum.sampleapplication.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
                      ActivityBindingModule::class,
                      AppModule::class
])
interface AppComponent:AndroidInjector<DaggerApplication> {

    override fun inject(instance: DaggerApplication?) {

    }

    @Component.Builder
    interface Builder{
       @BindsInstance
       fun application(application: Application):Builder

        fun build():AppComponent
    }

}