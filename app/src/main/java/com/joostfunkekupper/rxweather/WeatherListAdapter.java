package com.joostfunkekupper.rxweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class WeatherListAdapter extends ArrayAdapter<WeatherResponse> {

    public WeatherListAdapter(Context context) {
        super(context, 0, new ArrayList<WeatherResponse>());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout cityRow = (LinearLayout) convertView;
        ViewHolder viewHolder;

        if (cityRow == null) {
            cityRow = (LinearLayout) LayoutInflater.from(getContext())
                    .inflate(R.layout.row_city, parent, false);

            viewHolder = new ViewHolder(cityRow);
            cityRow.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) cityRow.getTag();
        }

        viewHolder.cityName.setText(cityCurrentWeather(getItem(position)));

        return cityRow;
    }

    public class ViewHolder {
        public final TextView cityName;

        public ViewHolder(View view) {
            cityName = (TextView) view.findViewById(R.id.city_name);
        }
    }

    private String cityCurrentWeather(WeatherResponse weatherResponse) {
        return String.format("%s - %.1f C",
                weatherResponse.name,
                weatherResponse.main.temp);
    }
}
