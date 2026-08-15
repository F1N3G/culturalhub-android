package com.g.culturalhub.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    // 10.0.2.2 = "localhost"-ul PC-ului tău, văzut din emulator. Trebuie să se termine cu "/".
    private const val BASE_URL = "http://10.0.2.2:8000/api/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // php artisan serve (Windows) e single-thread; keep-alive îl blochează.
    // "Connection: close" -> fiecare cerere se închide, serverul rămâne liber pentru următoarea.
    private val closeConnection = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .header("Connection", "close")
            .build()
        chain.proceed(request)
    }

    // public (val, nu private) ca să-l folosească și Coil, prin CulturalHubApp
    val okHttp: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(closeConnection)
        .addInterceptor(logging)
        .build()

    val api: CulturalHubApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttp)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CulturalHubApi::class.java)
}