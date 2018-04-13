package com.icoalert.di

import com.icoalert.ui.viewmodel.BaseViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ AppModule::class ])
interface AppComponent {

    @SuppressWarnings("LeakingThisInConstructor")
    fun inject(baseViewModel: BaseViewModel)

}