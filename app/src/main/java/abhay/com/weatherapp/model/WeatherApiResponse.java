package abhay.com.weatherapp.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abhayrajsingh on 10/10/17.
 */
@AutoValue
public abstract class WeatherApiResponse {

    @SerializedName("name")
    public abstract String cityName();

    @SerializedName("weather")
    public abstract List<Weather> weatherList();

    @SerializedName("main")
    public abstract Temperature main();

    public static TypeAdapter<WeatherApiResponse> typeAdapter(Gson gson){
        return new AutoValue_WeatherApiResponse.GsonTypeAdapter(gson);
    }
}
