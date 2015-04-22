package com.joostfunkekupper.rxweather.webservices;

import com.joostfunkekupper.rxweather.WeatherResponse;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface OpenWeatherMapClient {

    @GET("/weather")
    Observable<WeatherResponse> fetchWeatherByCityName(@Query("q") String cityName, @Query("units") String units);

    /**
     * Create an OpenWeatherMapClient singleton
     */
    public class Builder {
        public static OpenWeatherMapClient create() {
            return new RestAdapter.Builder()
                    .setEndpoint("http://api.openweathermap.org/data/2.5")
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build()
                    .create(OpenWeatherMapClient.class);
        }
    }
}
