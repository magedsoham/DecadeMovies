package com.maged.oldmovies

import android.app.Application
import com.maged.oldmovies.di.DaggerMyComponent
import com.maged.oldmovies.di.MyComponent
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class AppClass : Application() {

    companion object {
        lateinit var app: AppClass
        private lateinit var myComponent: MyComponent
    }

    override fun onCreate() {
        super.onCreate()

        app = this
        myComponent = createMyComponent()
    }

    fun getMyComponent(): MyComponent {
        return myComponent
    }

    private fun createMyComponent(): MyComponent {
        return DaggerMyComponent.builder().build()
    }
}