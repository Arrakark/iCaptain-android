package ca.pomogaev.icaptain;

import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import net.khirr.library.foreground.Foreground;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {

    AutopilotRestClient autopilot;

    private TextView mTextMessage;
    BottomNavigationView bottom_nav;

    private TextView textView1;
    //private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private TextView textView6;
    private TextView textView7;
    private TextView textView8;
    private TextView textView9;
    private TextView textView10;
    private TextView textView11;
    private TextView textView12;
    private TextView textView13;

    Runnable r;

    FragmentManager fragmentManager = getSupportFragmentManager();

    ManualFragment manualFragment = new ManualFragment();
    OfflineFragment offlineFragment = new OfflineFragment();
    AutopilotFragment autopilotFragment = new AutopilotFragment();


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.offline:
                    autopilot.setAutopilotState(1);
                    return true;
                case R.id.manual:
                    autopilot.setAutopilotState(2);
                    return true;
                case R.id.autopilot:
                    autopilot.setAutopilotState(3);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        autopilot = new AutopilotRestClient(this);
        bottom_nav = (BottomNavigationView) findViewById(R.id.navigation);
        textView1 = (TextView) findViewById(R.id.textView1);
        //textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);
        textView6 = (TextView) findViewById(R.id.textView6);
        textView7 = (TextView) findViewById(R.id.textView7);
        textView8 = (TextView) findViewById(R.id.textView8);
        textView9 = (TextView) findViewById(R.id.textView9);
        textView10 = (TextView) findViewById(R.id.textView10);
        textView11 = (TextView) findViewById(R.id.textView11);
        textView12 = (TextView) findViewById(R.id.textView12);
        textView13 = (TextView) findViewById(R.id.textView13);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Foreground.Companion.init(this.getApplication());


        final Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                //if (Foreground.Companion.isForeground()) {
                    //get autopilot data
                    autopilot.getAutopilotData();
                    //update text
                    updateLabels();
                    updateControlFragment();
                    //delay loop

                    switch (autopilot.state) {
                        case 0:
                            offlineFragment.updateHelpLabels();
                            break;
                        case 1:
                            offlineFragment.updateHelpLabels();
                            break;
                        case 2:
                            manualFragment.updateRudderAutopilot();
                            manualFragment.updateRudderText();
                            break;
                        case 3:
                            autopilotFragment.updateText();
                            break;
                    }
                    handler.postDelayed(this, 750);
                //}
            }

        };
        handler.postDelayed(r, 750);
    }

    public void updateControlFragment() {
        new Handler().post(new Runnable() {
            public void run() {

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                switch (autopilot.state) {
                    case 0:
                        fragmentTransaction.replace(R.id.frameLayout, offlineFragment);
                        break;
                    case 1:
                        fragmentTransaction.replace(R.id.frameLayout, offlineFragment);
                        break;
                    case 2:
                        fragmentTransaction.replace(R.id.frameLayout, manualFragment);
                        break;
                    case 3:
                        fragmentTransaction.replace(R.id.frameLayout, autopilotFragment);
                        break;
                }
                fragmentTransaction.commitAllowingStateLoss();
            }
        });
    }

    public void updateLabels() {
        switch (autopilot.connected) {
            case 1:
                textView4.setText("Connected");
                break;
            case 0:
                textView4.setText("Disconnected");
                break;
        }
        String status = "";
        switch (autopilot.state) {
            case 0:
                status = "Error";
                break;
            case 1:
                status = "Offline";
                break;
            case 2:
                status = "Manual Control";
                break;
            case 3:
                status = "Autopilot Control";
                break;
        }
        textView1.setText("Autopilot: " + status);
        //textView2.setText("Lat: " + Double.toString(autopilot.lat) + " Long: " + Double.toString(autopilot.lng));
        textView3.setText("GPS HDOP: " + Double.toString(autopilot.hdop));
        textView5.setText("Speed : " + Double.toString(autopilot.speed) + " knots");
        textView6.setText("Rudder position: " + Double.toString(autopilot.rudder_position));
        textView7.setText("Rudder control: " + Double.toString(autopilot.rudder_control));
        textView8.setText("P Gain: " + Double.toString(autopilot.p_gain));
        textView9.setText("D Gain: " + Double.toString(autopilot.d_gain));
        textView10.setText("Cross track E: " + Double.toString(autopilot.cross_track_error));
        textView11.setText("Direction E: " + Double.toString(autopilot.direction_error));
        textView12.setText("DD E: " + Double.toString(autopilot.dderror));
        textView13.setText("DD Gain: " + Double.toString(autopilot.ddgain));

    }

}
