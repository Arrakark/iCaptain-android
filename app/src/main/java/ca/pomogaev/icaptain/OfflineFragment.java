package ca.pomogaev.icaptain;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OfflineFragment extends Fragment {

    AutopilotRestClient autopilot;
    MainActivity activity;

    TextView help_label;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        activity = (MainActivity) getActivity();
        autopilot = activity.autopilot;

        rootView = inflater.inflate(R.layout.offline_fragment, container, false);


        help_label = (TextView) rootView.findViewById(R.id.textView2);
        // Inflate the layout for this fragment
        return rootView;
    }

    public void updateHelpLabels(){
        if (help_label != null) {
            if (autopilot.connected == 1){
                //autopilot is connected
                if (autopilot.state == 1){
                    help_label.setText("Change to manual or auto mode below");
                }
                else if (autopilot.state == 0){
                    help_label.setText("Autopilot has encountered an error");
                }
            }
            else {
                //autopilot is not connected; connect to WiFi network
                help_label.setText("Connect to 'autopilot' WiFi network");
            }
        }
    }
}
