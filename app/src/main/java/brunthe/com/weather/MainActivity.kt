package brunthe.com.weather

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import brunthe.com.weather.repository.WeatherRepo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        GlobalScope.launch { WeatherRepo().loadWeather("1668341") }
    }
}