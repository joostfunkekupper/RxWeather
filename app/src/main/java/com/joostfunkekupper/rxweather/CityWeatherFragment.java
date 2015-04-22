package com.joostfunkekupper.rxweather;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.joostfunkekupper.rxweather.webservices.OpenWeatherMapClient;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class CityWeatherFragment extends ListFragment {

    private OnFragmentInteractionListener mListener;

    private WeatherListAdapter adapter;

    private final OpenWeatherMapClient client = OpenWeatherMapClient.Builder.create();

    public static CityWeatherFragment newInstance() {
        return new CityWeatherFragment();
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CityWeatherFragment() {
    }

    Observable<List<String>> observableCityIds;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new WeatherListAdapter(getActivity());
        setListAdapter(adapter);


        Observable.from(Arrays.asList("Melbourne","Sydney"))
                .flatMap(new Func1<String, Observable<WeatherResponse>>() {
                    @Override
                    public Observable<WeatherResponse> call(String city) {
                        return client.fetchWeatherByCityName(city, "metric");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseAction);
    }

    private Action1<WeatherResponse> responseAction = new Action1<WeatherResponse>() {
        @Override
        public void call(WeatherResponse weatherResponse) {
            Log.e("Observable", "WeatherResponse: " + weatherResponse.name);

            if (weatherResponse.name != null) {
                adapter.add(weatherResponse);
                adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(adapter.getItem(position).name);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

}
