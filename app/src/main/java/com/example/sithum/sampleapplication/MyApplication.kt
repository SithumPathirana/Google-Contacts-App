package com.example.sithum.sampleapplication



import com.example.sithum.sampleapplication.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.realm.Realm
import io.realm.RealmConfiguration


class MyApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        Realm.init(this)
        val realmConfig=RealmConfiguration.Builder()
            .name("contacts.realm")
            .schemaVersion(0)
            .build()

        Realm.setDefaultConfiguration(realmConfig)
    }

    companion object {
        var instance: MyApplication?=null
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}