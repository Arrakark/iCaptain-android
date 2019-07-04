package ca.pomogaev.icaptain;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AutopilotFragment extends Fragment implements View.OnClickListener {

    AutopilotRestClient autopilot;
    private TextView textView1;
    private TextView textView2;
    MainActivity activity;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        activity = (MainActivity) getActivity();
        autopilot = activity.autopilot;

        rootView = inflater.inflate(R.layout.autopilot_fragment, container, false);


        Button threeleft = (Button) rootView.findViewById(R.id.button5);
        threeleft.setOnClickListener(this); // calling onClick() method
        Button twoleft = (Button) rootView.findViewById(R.id.button4);
        twoleft.setOnClickListener(this);
        Button oneleft = (Button) rootView.findViewById(R.id.button3);
        oneleft.setOnClickListener(this);
        Button threeright = (Button) rootView.findViewById(R.id.button6);
        threeright.setOnClickListener(this); // calling onClick() method
        Button tworight = (Button) rootView.findViewById(R.id.button);
        tworight.setOnClickListener(this);
        Button oneright = (Button) rootView.findViewById(R.id.button2);
        oneright.setOnClickListener(this);

        Button b7 = (Button) rootView.findViewById(R.id.button7);
        b7.setOnClickListener(this);

        Button b8 = (Button) rootView.findViewById(R.id.button8);
        b8.setOnClickListener(this);

        Button b9 = (Button) rootView.findViewById(R.id.button9);
        b9.setOnClickListener(this);

        Button b10 = (Button) rootView.findViewById(R.id.button10);
        b10.setOnClickListener(this);

        Button b11 = (Button) rootView.findViewById(R.id.button11);
        b11.setOnClickListener(this);

        Button b12 = (Button) rootView.findViewById(R.id.button12);
        b12.setOnClickListener(this);

        return rootView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button5:
                autopilot.nudgeAutopilotHeading(-25);
                break;
            case R.id.button4:
                autopilot.nudgeAutopilotHeading(-10);
                break;
            case R.id.button3:
                autopilot.nudgeAutopilotHeading(-1);
                break;
            case R.id.button2:
                autopilot.nudgeAutopilotHeading(1);
                break;
            case R.id.button:
                autopilot.nudgeAutopilotHeading(10);
                break;
            case R.id.button6:
                autopilot.nudgeAutopilotHeading(25);
                break;
            case R.id.button7:
                autopilot.pminus();
                break;
            case R.id.button8:
                autopilot.pplus();
                break;
            case R.id.button9:
                autopilot.dminus();
                break;
            case R.id.button10:
                autopilot.dplus();
                break;
            case R.id.button11:
                autopilot.ddminus();
                break;
            case R.id.button12:
                autopilot.ddplus();
                break;
        }
        updateText();

    }

    public void updateText() {
        if (rootView != null) {
            textView1 = (TextView) rootView.findViewById(R.id.heading_text);
            textView2 = (TextView) rootView.findViewById(R.id.course_text);
            textView1.setText("Heading: " + String.valueOf(Math.round(autopilot.heading)) + "°");
            textView2.setText("Course: " + String.valueOf(Math.round(autopilot.course)) + "°");
        }
    }

}
