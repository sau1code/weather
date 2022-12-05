package brunthe.com.weather.ui.weatherDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import brunthe.com.weather.model.WeatherResponse
import brunthe.com.weather.repository.WeatherRepo
import kotlinx.coroutines.launch

class WeatherDetailViewModel(
    cityId: String? = "1668341", val cityName: String? = "Taipei") : ViewModel() {

    private var weatherRepo = WeatherRepo()
    val weather: LiveData<WeatherResponse> get() = _weather
    private var _weather = MutableLiveData<WeatherResponse>()

    init {
        cityId?.let {
            viewModelScope.launch {
                weatherRepo.loadWeather(it)?.let {
                    _weather.value = it
                }
            }
        }
    }

}








