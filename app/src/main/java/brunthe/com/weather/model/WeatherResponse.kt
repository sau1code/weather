package brunthe.com.weather.model

data class WeatherResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<X>,
    val message: Int
)