package brunthe.com.weather.repository

import android.util.Log
import brunthe.com.weather.model.WeatherResponse
import brunthe.com.weather.service.WeatherAPIService

class WeatherRepo {
    private val weatherAPIService = WeatherAPIService()

    suspend fun loadWeather(cityId: String): WeatherResponse? {
        return kotlin.runCatching { weatherAPIService.getForecast(cityId) }.fold(
            onSuccess = {
                Log.d("WeatherRepo", "onSuccess-it = $it")
                if (it.isSuccessful) {
                    var body = it.body()
                    if (body == null || body.cod != "200") {
                        return null
                    }
                    return body
                } else {
                    return null
                }
            },
            onFailure = {
                Log.d("WeatherRepo", "onFailure-it = $it")
                null }
        )
    }
}