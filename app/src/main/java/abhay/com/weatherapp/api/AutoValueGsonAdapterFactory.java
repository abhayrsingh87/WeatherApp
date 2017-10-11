package abhay.com.weatherapp.api;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;


/**
 * Created by abhayrajsingh on 10/10/17.
 * This class provides TypeAdaptersFactory for AutoValue Data models.
 * This will be used while providing Gson object for retrofit API
 */
@GsonTypeAdapterFactory
public abstract class AutoValueGsonAdapterFactory implements TypeAdapterFactory{

    public static TypeAdapterFactory create(){
        return new AutoValueGson_AutoValueGsonAdapterFactory();
    }
}
