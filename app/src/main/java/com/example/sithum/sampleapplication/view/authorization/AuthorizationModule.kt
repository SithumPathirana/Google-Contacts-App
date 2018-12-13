package com.example.sithum.sampleapplication.view.authorization

import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
abstract class AuthorizationModule{

    @Binds
    abstract fun view(authorizationActivity: AuthorizationActivity):AuthorizationContract.View

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideAuthorizationPresenter(view:AuthorizationContract.View):AuthorizationContract.Presenter{
            return AuthorizationPresenter(view)
        }
    }
}