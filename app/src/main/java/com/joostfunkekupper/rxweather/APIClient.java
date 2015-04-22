package com.joostfunkekupper.rxweather;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface APIClient {

    @GET("/weather")
    Observable<WeatherResponse> fetchWeatherByCityName(@Query("q") String cityName, @Query("units") String units);
}
