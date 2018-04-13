package com.icoalert

import android.app.Application
import android.databinding.DataBindingUtil
import com.icoalert.ui.viewmodel.ViewModelsBindings
import com.icoalert.di.AppComponent
import com.icoalert.di.AppModule
import com.icoalert.di.DaggerAppComponent

class App : Application() {

    lateinit var icoAlertComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        DataBindingUtil.setDefaultComponent(ViewModelsBindings())
        icoAlertComponent = initDagger(this)
    }


    private fun initDagger(app: App): AppComponent =
            DaggerAppComponent.builder()
                    .appModule(AppModule(app))
                    .build()
}