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
                    handler.postDelayed(this, 300);
                //}
            }

        };
        handler.postDelayed(r, 300);
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
        textView6.setText("Rudder: " + Double.toString(autopilot.rudder_speed));
    }

}
