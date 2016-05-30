package com.vrmightypirates.smallfhemcontrol;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.vrmightypirates.smalfhemcontrol.R;

/**
 * Created by Boke on 30.05.2016.
 */
public class PlaceholderFragment extends Fragment implements FhemMessageParser.DeviceStatusChangedListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String TAG = PlaceholderFragment.class.getSimpleName();
    private SeekBar temperatureControlBathroom = null;
    API api = new API();
    View rootView;
    private TextView currentTemperatureBathroomTextView;

    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);


        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_smal_fhem_control_main, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        currentTemperatureBathroomTextView = (TextView) rootView.findViewById(R.id.themperatureHeater);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        temperatureControlBathroom = (SeekBar) rootView.findViewById(R.id.seekBarHeater);
        temperatureControlBathroom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

                Log.v("Progress changed", "Font size: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.v("Start touching", "Font size: " + seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                Log.v("Stop touching", "Font size: " + seekBar.getProgress());
            }

        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        api.initConnection(ConnectionType.telnet);
        DeviceHeaterMax deviceHeaterMaxBathroom = new DeviceHeaterMax("BZ.HT.BadHeizung", currentTemperatureBathroomTextView);
        api.addDeviceToUpdateListener(deviceHeaterMaxBathroom);
        api.startAutoUpdate();
        api.getParser().addToDeviceChangeListener(this);

    }

    @Override
    public void onDeviceStatusChange(final FhemDevice device) {
        Log.i(TAG, "onDeviceStatusChange: " + device.getDeviceName());
        final DeviceHeaterMax deviceHeaterMax = (DeviceHeaterMax)device;
        final TextView textView = (TextView) deviceHeaterMax.getWidget();
        textView.setText(deviceHeaterMax.getDesireTemperature());
    }
}
