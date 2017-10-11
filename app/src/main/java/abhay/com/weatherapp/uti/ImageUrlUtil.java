package abhay.com.weatherapp.uti;

import abhay.com.weatherapp.BuildConfig;
import abhay.com.weatherapp.model.Weather;

/**
 * Created by abhayrajsingh on 10/11/17.
 * This utility class creates absolute url of weather image
 */
public class ImageUrlUtil {

    private static final String IMAGE_MIME_TYPE_SUFFIX = ".png";
    public static String getFullImageUrl(Weather weather){
        return new StringBuilder(BuildConfig.IMAGE_API_BASE_URL)
                .append(weather.weatherIcon()).append(IMAGE_MIME_TYPE_SUFFIX).toString();
    }
}
