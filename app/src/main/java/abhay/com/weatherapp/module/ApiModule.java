package abhay.com.weatherapp.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import abhay.com.weatherapp.BuildConfig;
import abhay.com.weatherapp.api.AutoValueGsonAdapterFactory;
import abhay.com.weatherapp.api.WeatherSearchApi;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by abhayrajsingh on 10/10/17.
 * This class provides Network dependencies
 */
@Module
public class ApiModule {

    @Singleton
    @Provides
    Gson provideGson(){
        return new GsonBuilder()
                .registerTypeAdapterFactory(AutoValueGsonAdapterFactory.create())
                .create();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(Gson gson){
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.SEARCH_API_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Singleton
    @Provides
    WeatherSearchApi provideWeatherSearchApi(Retrofit retrofit){
        return retrofit.create(WeatherSearchApi.class);
    }


}
