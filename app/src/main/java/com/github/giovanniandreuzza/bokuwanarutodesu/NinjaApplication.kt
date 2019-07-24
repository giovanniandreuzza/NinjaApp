package com.github.giovanniandreuzza.bokuwanarutodesu

import android.app.Application
import com.github.giovanniandreuzza.data.RestClient

class NinjaApplication : Application() {

    companion object {
        lateinit var instance: NinjaApplication
            private set
    }


    private val BASE_URL =
        "http://www.bestanimeseries.org/DDL/ANIME/NarutoShippuden/Naruto/"


    lateinit var restClient: RestClient

    // http://www.bestanimeseries.org/DDL/ANIME/NarutoShippuden/Naruto/Naruto_Ep_065_SUB_ITA.mp4


    override fun onCreate() {
        super.onCreate()
        instance = this

        restClient = RestClient.getInstance(this, BASE_URL)
    }

}