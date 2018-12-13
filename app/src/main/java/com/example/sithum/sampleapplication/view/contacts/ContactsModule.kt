package com.example.sithum.sampleapplication.view.contacts

import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class ContactsModule{

    @Binds
    abstract fun view(contactsActivity: Contacts): ContactsContract.View


    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideContactsPresenter(view: ContactsContract.View): ContactsContract.Presenter {
            return ContactsPresenter(view)
        }
    }
}