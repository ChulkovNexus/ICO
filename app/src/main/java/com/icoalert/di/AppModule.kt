package com.icoalert.di

import android.content.Context
import com.icoalert.App
import com.icoalert.api.data.DataManager
import com.icoalert.api.requests.http.HttpClient
import com.icoalert.api.ws.WsConnection
import dagger.Module
import javax.inject.Singleton
import dagger.Provides

@Module
class AppModule(private val app: App) {

    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideDataManager(): DataManager = DataManager()

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient = HttpClient()

    @Provides
    @Singleton
    fun provideWsConnection(): WsConnection = WsConnection(app)

}