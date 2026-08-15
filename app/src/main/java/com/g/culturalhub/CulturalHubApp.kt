package com.g.culturalhub

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.g.culturalhub.data.remote.ApiClient

class CulturalHubApp : Application(), ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader =
        ImageLoader.Builder(this)
            .okHttpClient(ApiClient.okHttp)
            .build()
}