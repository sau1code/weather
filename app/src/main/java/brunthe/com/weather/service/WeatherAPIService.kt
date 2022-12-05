package brunthe.com.weather.service

import brunthe.com.weather.model.WeatherResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val APP_ID = "1b33c5116a240eede6d8ad91da693953"
const val BASE_URL = "https://api.openweathermap.org/"
// https://api.openweathermap.org/data/2.5/forecast?id=1668341&appid=1b33c5116a240eede6d8ad91da693953&lang=zh_tw&units=metric&model=json

interface WeatherAPIService {
    @GET("data/2.5/forecast")
    suspend fun getForecast(@Query("id") id: String): Response<WeatherResponse>

    companion object {

        operator fun invoke(): WeatherAPIService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("appid", APP_ID)
//                    .addQueryParameter("lang", "zh_tw")
                    .addQueryParameter("units", "metric")
                    .addQueryParameter("model", "json")
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherAPIService::class.java)
        }
    }
}