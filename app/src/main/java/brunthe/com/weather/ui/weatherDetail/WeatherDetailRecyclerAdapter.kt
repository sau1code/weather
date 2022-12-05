package brunthe.com.weather.ui.weatherDetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import brunthe.com.weather.model.WeatherResponse
import brunthe.com.weather.model.X
import brunthe.com.weather.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.weather_detail_item.view.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*


class WeatherDetailRecyclerAdapter(var data: MutableList<X>):
    RecyclerView.Adapter<WeatherDetailRecyclerAdapter.WeatherDetailRecyclerHolder>() {

    class WeatherDetailRecyclerHolder(itemView: View): RecyclerView.ViewHolder(itemView){ }

    override fun getItemCount(): Int { return data.size }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherDetailRecyclerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_detail_item, parent, false)
        return WeatherDetailRecyclerHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherDetailRecyclerHolder, position: Int) {
        val weather = data[position]
        holder.itemView.temperature.text = weather.main.temp.toInt().toString() + "°C"
        val date = Date(weather.dt * 1000)
        val localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
        val hour = localDateTime.hour
        if (hour < 10) holder.itemView.time.text = "0${hour}:00"
        else holder.itemView.time.text = "${hour}:00"
        if (hour < 3)
            holder.itemView.date.text = "${localDateTime.month.value}/${localDateTime.dayOfMonth}"
        else
            holder.itemView.date.text = ""

        Glide.with(holder.itemView)
            .load("https://openweathermap.org/img/wn/" + weather.weather[0].icon + "@2x.png")
            .into(holder.itemView.weather_icon)
    }

    fun updateData(response: WeatherResponse?) {
        response?.let {
            data = response.list as MutableList<X>
            notifyDataSetChanged()
        }
    }

}















