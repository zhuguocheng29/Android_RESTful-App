package edu.monash.fit5183;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.monash.fit5183.Model.ApplicationUser;
import edu.monash.fit5183.Util.DatabaseHelper;

/**
 * Created by Think on 4/23/2016.
 */
public class TrackCalorie_fragment extends Fragment {
    View trackView;
    private TextView frag_track_goal;
    private TextView frag_track_totalconsumed;
    private TextView frag_track_food;
    private TextView frag_track_nat;
    private TextView frag_track_remaining;
    private TextView frag_track_steps;
    private TextView frag_track_stepsCalorie;
    private TextView frag_track_restCalorie;
    private ProgressBar progressBar;
    private Handler handler;

    String username = "";
    int total_steps;
    String date1 = "";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        trackView = inflater.inflate(R.layout.fragment_track_calorie, container, false);

        frag_track_goal = (TextView)trackView.findViewById(R.id.frag_track_goal);
        frag_track_remaining = (TextView)trackView.findViewById(R.id.frag_track_remaining);
        frag_track_totalconsumed = (TextView)trackView.findViewById(R.id.frag_track_totalconsumed);
        frag_track_food = (TextView)trackView.findViewById(R.id.frag_track_food);
        frag_track_nat = (TextView)trackView.findViewById(R.id.frag_track_nat);
        frag_track_steps = (TextView)trackView.findViewById(R.id.frag_track_steps);
        frag_track_stepsCalorie = (TextView)trackView.findViewById(R.id.frag_track_stepsCalorie);
        frag_track_restCalorie = (TextView)trackView.findViewById(R.id.frag_track_restCalorie);
        progressBar = (ProgressBar)trackView.findViewById(R.id.frag_track_progressbar);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                progressBar.setProgress(msg.what);
                return false;
            }
        });


        //frag_track_goal.setText("");
        //frag_track_goal.setText("1111");
        ApplicationUser user = new ApplicationUser();
        username = user.getUsername();

        //get currtent time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        date1 = sdf.format(date);

        //get data
        new GetReport().execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.report/Report.findByUsernameAReportDate/" + username+"/"+date1);
        return trackView;
    }

    private class GetReport extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... args) {
            URL url;
            HttpURLConnection conn = null;
            try {
                url = new URL(args[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                String resmsg = "";
                InputStream instream = new BufferedInputStream(conn.getInputStream());
                BufferedReader buffer = new BufferedReader(new InputStreamReader(instream));
                StringBuilder sb = new StringBuilder();
                while ((resmsg = buffer.readLine()) != null) {
                    sb.append(resmsg);
                }
                instream.close();
                buffer.close();
                // Log.i("Respones is ", resmsg);
                return sb.toString();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONArray userContents = new JSONArray(result);
                    JSONObject reportJson = userContents.getJSONObject(0);
                    int totalSteps = reportJson.getInt("totalSteps");
                    float stepsCalorie = (float) reportJson.getDouble("stepsCalorie");
                    float restCalorie = (float) reportJson.getDouble("restCalorie");
                    float totalCalorieConsumed = (float) reportJson.getDouble("totalCalorieConsumed");
                    float totalFoodCalorie = (float) reportJson.getDouble("totalFoodCalorie");
                    int calorieGoal = reportJson.getInt("calorieGoal");
                    float remaining = (float) reportJson.getDouble("remaining");
                    float nat = totalCalorieConsumed - totalFoodCalorie;

                    frag_track_goal.setText(calorieGoal+"");
                    frag_track_totalconsumed.setText(totalCalorieConsumed+"");
                    frag_track_food.setText(totalFoodCalorie+"");
                    frag_track_nat.setText(nat + "");
                    frag_track_remaining.setText("remaining calorie : "+remaining);
                    frag_track_steps.setText(totalSteps+"");
                    frag_track_stepsCalorie.setText(stepsCalorie+"");
                    frag_track_restCalorie.setText(restCalorie+"");

                    float burned = totalCalorieConsumed - totalFoodCalorie;
                    float percent = burned / calorieGoal;
                    int per = (int)(percent * 100);
                    handler.sendEmptyMessage(per);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }





}
