package com.joostfunkekupper.rxweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<Weather> {

    public CustomListAdapter(Context context) {
        super(context, 0, new ArrayList<Weather>());
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

        viewHolder.cityName.setText(getItem(position).name);

        return cityRow;
    }

    public class ViewHolder {
        public final TextView cityName;

        public ViewHolder(View view) {
            cityName = (TextView) view.findViewById(R.id.city_name);
        }
    }
}
