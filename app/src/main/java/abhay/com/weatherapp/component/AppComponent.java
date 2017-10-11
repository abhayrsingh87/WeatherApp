package abhay.com.weatherapp.component;

import javax.inject.Singleton;

import abhay.com.weatherapp.module.ApiModule;
import abhay.com.weatherapp.module.DataModule;
import abhay.com.weatherapp.ui.WeatherSearchActivity;
import dagger.Component;

/**
 * Created by abhayrajsingh on 10/10/17.
 */
@Singleton
@Component(modules = {ApiModule.class, DataModule.class})
public interface AppComponent {
    void inject(WeatherSearchActivity weatherSearchActivity);
}
