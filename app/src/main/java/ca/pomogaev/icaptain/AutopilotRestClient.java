package ca.pomogaev.icaptain;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.*;

import org.json.JSONException;
import org.json.JSONObject;

public class AutopilotRestClient {

    int connected;
    int state;
    double heading;
    double waypoint_lng;
    double waypoint_lat;
    double lat;
    double lng;
    double hdop;
    double course;
    double speed;
    int sat_count;
    double rudder_speed;
    double cross_track_error;
    double direction_error;
    RequestQueue queue;

    Context main_context;

    AutopilotRestClient(final Context set_context) {
        main_context = set_context;
        queue = Volley.newRequestQueue(main_context);
        state = 0;
        heading = 0;
        waypoint_lng = 0;
        lat = 0;
        lng = 0;
        hdop = 0;
        course = 0;
        speed = 0;
        sat_count = 0;
        rudder_speed = 0;
        cross_track_error = 0;
        direction_error = 0;
        connected = 0;
    }

    public void getAutopilotData() {
        // Instantiate the RequestQueue.
        String url = "http://192.168.0.1/";
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (null != response) {
                            connected = 1;
                            Log.d("AutopilotAPI", "Got Data");
                            try {
                                JSONObject data = response.getJSONObject("variables");
                                state = data.getInt("state");
                                heading = data.getDouble("heading");
                                //waypoint_lng = data.getDouble("waypoint_long");
                                //waypoint_lat = data.getDouble("waypoint_lat");
                                lat = data.getDouble("lat");
                                lng = data.getDouble("long");
                                hdop = data.getDouble("hdop");
                                course = data.getDouble("course");
                                speed = data.getDouble("speed");
                                sat_count = data.getInt("state");
                                rudder_speed = data.getDouble("rudder_speed");
                                //cross_track_error = data.getDouble("cross_track_error");
                                //direction_error = data.getDouble("direction_error");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                state = 0;
                connected = 0;
            }
        });
        queue.add(request);
    }

    public void setAutopilotState(final int set_state) {
        // Instantiate the RequestQueue.
        String url = "http://192.168.0.1/set_state?" + String.valueOf(set_state);
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (null != response) {
                            Log.d("AutopilotAPI", "Set State to " + String.valueOf(set_state));
                            try {
                                if (response.getInt("return_value") == 1) {
                                    //Toast.makeText(main_context,
                                    //        "Set Mode " + String.valueOf(set_state), Toast.LENGTH_SHORT).show();
                                    state = set_state;
                                } else {
                                    Toast.makeText(main_context,
                                            "Cannot Set Mode " + String.valueOf(set_state), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {

                                Toast.makeText(main_context,
                                        "Cannot Set Mode " + String.valueOf(set_state), Toast.LENGTH_SHORT).show();

                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                connected = 0;
            }
        });
        queue.add(request);
    }

    public void setAutopilotHeading(final int new_heading){
        // Instantiate the RequestQueue.
        String url = "http://192.168.0.1/set_heading?" + String.valueOf(new_heading);
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("AutopilotAPI", "Set heading to " + String.valueOf(new_heading));
                        if (null != response) {
                            try {
                                if (response.getInt("return_value") == 1) {
                                    //Toast.makeText(main_context,
                                    //        "Set Mode " + String.valueOf(set_state), Toast.LENGTH_SHORT).show();
                                    heading = new_heading;
                                } else {
                                    Toast.makeText(main_context,
                                            "Cannot Set Heading " + String.valueOf(heading), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {

                                Toast.makeText(main_context,
                                        "Cannot Set Heading " + String.valueOf(heading), Toast.LENGTH_SHORT).show();

                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                connected = 0;
            }
        });
        queue.add(request);
    }

    public void nudgeAutopilotHeading(int nudge){
        int new_heading = ((int)heading + nudge)%360;
        if (new_heading < 0){
            new_heading = new_heading + 360;
        }
        setAutopilotHeading(new_heading);
    }

    public void setAutopilotRudder(final int manual_rudder){
        // Instantiate the RequestQueue.
        String url = "http://192.168.0.1/manual_control?" + String.valueOf(manual_rudder);
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("AutopilotAPI", "Set rudder to " + String.valueOf(manual_rudder));
                        if (null != response) {
                            try {
                                if (response.getInt("return_value") == 1) {
                                    //Toast.makeText(main_context,
                                    //        "Set Mode " + String.valueOf(set_state), Toast.LENGTH_SHORT).show();
                                    rudder_speed = manual_rudder;
                                } else {
                                    //Toast.makeText(main_context,
                                    //        "Cannot Set Rudder " + String.valueOf(manual_rudder), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {

                                //Toast.makeText(main_context,
                                       // "Cannot Set Rudder " + String.valueOf(manual_rudder), Toast.LENGTH_SHORT).show();

                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                connected = 0;
            }
        });
        queue.add(request);
    }

}
