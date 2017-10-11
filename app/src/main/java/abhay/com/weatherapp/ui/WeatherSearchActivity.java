package abhay.com.weatherapp.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import abhay.com.weatherapp.R;
import abhay.com.weatherapp.api.WeatherSearchApi;
import abhay.com.weatherapp.component.DaggerAppComponent;
import abhay.com.weatherapp.model.Temperature;
import abhay.com.weatherapp.model.Weather;
import abhay.com.weatherapp.model.WeatherApiResponse;
import abhay.com.weatherapp.module.DataModule;
import abhay.com.weatherapp.uti.ImageUrlUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * This Activity provides user interface to search weather condition of a given city.
 * It displays weather condition and temperature
 */

public class WeatherSearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private static final String LAST_SEARCHED_CITY = "last_searched_city";
    private static final String DEFAULT_SEARCH_TEXT = "";
    @Inject
    WeatherSearchApi mWeatherSearchApi;
    @Inject SharedPreferences mSharedPreferences;
    @BindView(R.id.error_layout)
    View mErrorLayout;
    @BindView(R.id.weather_layout)
    View mWeatherLayout;
    @BindView(R.id.city_name_text_view)
    TextView mCityNameTextView;
    @BindView(R.id.min_temp_text_view)
    TextView mMinTempTextView;
    @BindView(R.id.max_temp_text_view)
    TextView mMaxTempTextView;
    @BindView(R.id.weather_image_view)
    ImageView mWeatherImageView;
    @BindView(R.id.weather_description_text_View)
    TextView mWeatherDescriptionTextView;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private String mLastSearchedCity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_search);
        ButterKnife.bind(this);
        DaggerAppComponent.builder()
                .dataModule(new DataModule(getApplication()))
                .build().inject(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mLastSearchedCity = getLastSearchedCity();
        if(!TextUtils.isEmpty(mLastSearchedCity)){
            getWeather(getLastSearchedCity());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        setLastSearchedCity(mLastSearchedCity);
        mCompositeDisposable.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mLastSearchedCity = query;
        getWeather(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    /**
     * This function calls weather search API and updates weather result Asynchronously
     * @param searchText city searched
     */
    private void getWeather(String searchText){
            mCompositeDisposable.add(getWeatherObservable(searchText)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(getWeatherObserver()));
    }

    /**
     * This function returns Rx Observable for search result
     * @param searchText city searched
     * @return Result Observable
     */
    private Observable<WeatherApiResponse> getWeatherObservable(String searchText){
        return mWeatherSearchApi.getWeather(searchText);
    }

    /**
     * This function returns Subscriber to listen weather results
     * @return result observer
     */
    private DisposableObserver<WeatherApiResponse> getWeatherObserver(){
        return new DisposableObserver<WeatherApiResponse>() {
            @Override
            public void onNext(WeatherApiResponse weatherApiResponse) {
                updateWeatherDetails(weatherApiResponse);
            }

            @Override
            public void onError(Throwable e) {
                showError();
            }

            @Override
            public void onComplete() {

            }
        };
    }

    /**
     * This function updates weather information on UI
     * @param weatherApiResponse
     */
    private void updateWeatherDetails(WeatherApiResponse weatherApiResponse){

        mWeatherLayout.setVisibility(View.VISIBLE);
        mErrorLayout.setVisibility(View.GONE);

        mCityNameTextView.setText(getString(R.string.city_name, weatherApiResponse.cityName()));

        Temperature temperature = weatherApiResponse.main();
        if(temperature != null){
            mMinTempTextView.setText(getString(R.string.min_temp, temperature.minTemp()));
            mMaxTempTextView.setText(getString(R.string.max_temp, temperature.maxTemp()));
        }

        if(weatherApiResponse.weatherList() != null && weatherApiResponse.weatherList().size() >0){
            Weather weather = weatherApiResponse.weatherList().get(0);
            Glide.with(this).load(ImageUrlUtil.getFullImageUrl(weather)).into(mWeatherImageView);
            mWeatherDescriptionTextView.setText(weather.weatherDescription());
        }
    }

    /**
     * This function is responsible for displaying error if weather search fails
     */
    private void showError(){
        mWeatherLayout.setVisibility(View.GONE);
        mErrorLayout.setVisibility(View.VISIBLE);
    }

    /**
     * This function return last city user searched to see weather information
     * @return Last city searched
     */
    private String getLastSearchedCity(){
       return mSharedPreferences.getString(LAST_SEARCHED_CITY, DEFAULT_SEARCH_TEXT);
    }

    /**
     * This function saves last city searched by user
     * @param cityName CityName
     */
    private void setLastSearchedCity(String cityName){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(LAST_SEARCHED_CITY, cityName);
        editor.commit();
    }
}
