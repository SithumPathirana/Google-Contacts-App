package com.example.sithum.sampleapplication.di

import com.example.sithum.sampleapplication.di.scopes.PerActivity
import com.example.sithum.sampleapplication.view.authorization.AuthorizationActivity
import com.example.sithum.sampleapplication.view.authorization.AuthorizationModule
import com.example.sithum.sampleapplication.view.contacts.Contacts
import com.example.sithum.sampleapplication.view.contacts.ContactsModule
import com.example.sithum.sampleapplication.view.login.LoginModule
import com.example.sithum.sampleapplication.view.login.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
interface ActivityBindingModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [ContactsModule::class] )
    abstract fun contactsActivityInjecter():Contacts

     @PerActivity
     @ContributesAndroidInjector(modules = [LoginModule::class])
     abstract fun loginActivityInjecter():MainActivity

    @PerActivity
     @ContributesAndroidInjector(modules = [AuthorizationModule::class])
     abstract fun authorizationActivityInjecter():AuthorizationActivity
}