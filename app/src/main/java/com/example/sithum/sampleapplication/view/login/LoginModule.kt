package com.example.sithum.sampleapplication.view.login

import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class LoginModule{

    @Binds
    abstract fun view(loginActivity: MainActivity):LoginContract.View

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideLoginPresenter(view: LoginContract.View): LoginContract.Presenter {
            return  LoginPresenter(view)
        }
    }

}