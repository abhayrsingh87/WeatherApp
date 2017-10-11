package abhay.com.weatherapp.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abhayrajsingh on 10/10/17.
 * This model class holds temperature data for weather
 */
@AutoValue
public abstract class Temperature {

    @SerializedName("temp")
    public abstract Float temp();

    @SerializedName("temp_min")
    public abstract Float minTemp();

    @SerializedName("temp_max")
    public abstract Float maxTemp();

    public static TypeAdapter<Temperature> typeAdapter(Gson gson){
        return new AutoValue_Temperature.GsonTypeAdapter(gson);
    }
}
