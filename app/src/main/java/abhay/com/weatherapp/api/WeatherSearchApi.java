package abhay.com.weatherapp.api;

import abhay.com.weatherapp.BuildConfig;
import abhay.com.weatherapp.model.WeatherApiResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by abhayrajsingh on 10/10/17.
 * This Retrofit interface is responsible for fetching weather details of a given city
 */

public interface WeatherSearchApi {

    @GET("weather?APPID=" + BuildConfig.APPID)
    Observable<WeatherApiResponse> getWeather(@Query("q") String searchText);
}
