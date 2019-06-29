package ca.pomogaev.icaptain;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class ManualFragment extends Fragment {

    AutopilotRestClient autopilot;
    MainActivity activity;

    SeekBar rudder_controller;
    TextView rudder_status;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        activity = (MainActivity) getActivity();
        autopilot = activity.autopilot;

        rootView = inflater.inflate(R.layout.manual_fragment, container, false);

        rudder_controller = (SeekBar) rootView.findViewById(R.id.seekBar);
        rudder_status = (TextView) rootView.findViewById(R.id.textview);
        setSeekbarListener();

        // Inflate the layout for this fragment
        return rootView;
    }

    public void updateRudderAutopilot() {
        if (rudder_controller != null) {
            autopilot.rudder_speed = (rudder_controller.getProgress() - 5) * 20;
            autopilot.setAutopilotRudder((int) autopilot.rudder_speed);
        }
    }

    public void updateRudderText() {
        if (rudder_status != null) {
            rudder_status.setText("Rudder: " + String.valueOf(autopilot.rudder_speed));
        }
    }

    public void setSeekbarListener() {
        rudder_controller.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //snap seekbar to middle
                //seekBar.setProgress(seekBar.getMax() / 2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //autopilot.setAutopilotRudder(seekBar.getProgress() - 100);
                //updateRudderAutopilot();
                //updateRudderText();
            }
        });
    }
}
