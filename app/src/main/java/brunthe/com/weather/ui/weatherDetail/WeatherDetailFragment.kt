package brunthe.com.weather.ui.weatherDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import brunthe.com.weather.R
import brunthe.com.weather.databinding.FragmentWeatherDetailBinding
import com.bumptech.glide.Glide
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.*

class WeatherDetailFragment : Fragment() {

    companion object { fun newInstance() = WeatherDetailFragment() }
    private lateinit var viewModel: WeatherDetailViewModel
    private lateinit var binding: FragmentWeatherDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather_detail, container, false)
        binding.lifecycleOwner = this
        return binding.root
//        return inflater.inflate(R.layout.fragment_weather_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(WeatherDetailViewModel::class.java)
        binding.viewModel = viewModel
        val adapter = WeatherDetailRecyclerAdapter(arrayListOf())
        binding.list.apply {
            this.adapter = adapter
        }
        binding.collapsingtoolbarlayout.setCollapsedTitleTextColor(resources.getColor(R.color.white, null))
        binding.collapsingtoolbarlayout.setExpandedTitleColor(resources.getColor(R.color.white, null))

        viewModel.weather.observe(viewLifecycleOwner, Observer {
            adapter.updateData(it)
            if (it.list.isNotEmpty()) {
                binding.weatherTemp.text = "${it.list[0].main.temp.toInt()}"
                binding.weatherDescription.text = "${it.list[0].weather[0].description}"

                val date = Date(it.list[0].dt * 1000)
                val localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
                val month = localDateTime.month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                val week = localDateTime.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                binding.weatherDatetime.text = "$week, $month ${localDateTime.dayOfMonth}"

                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/"+ it.list[0].weather[0].icon + "@2x.png")
                    .into(binding.weatherPic)
            }
        })

        binding.weatherBack.setOnClickListener{
            activity?.onBackPressed()
        }
    }

}


























