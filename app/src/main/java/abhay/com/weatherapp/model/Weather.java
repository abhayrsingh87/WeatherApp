package abhay.com.weatherapp.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abhayrajsingh on 10/10/17.
 * This model class holds weather image icon and description
 */
@AutoValue
public abstract class Weather {

    @SerializedName("id")
    public abstract String id();

    @SerializedName("description")
    public abstract String weatherDescription();

    @SerializedName("icon")
    public abstract String weatherIcon();

    public static TypeAdapter<Weather> typeAdapter(Gson gson){
        return new AutoValue_Weather.GsonTypeAdapter(gson);
    }

}
