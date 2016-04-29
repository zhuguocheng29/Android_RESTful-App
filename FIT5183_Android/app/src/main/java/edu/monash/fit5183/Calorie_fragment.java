package edu.monash.fit5183;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.monash.fit5183.CallServer.UpdateRemaining;
import edu.monash.fit5183.Model.ApplicationUser;

/**
 * Created by Think on 4/15/2016.
 */

public class Calorie_fragment extends Fragment {

    View vCalorie;
    private TextView frag_calorie;
    private TextView frag_calorieGoal;
    private EditText frag_calorie_edit;
    private Button frag_calorie_check;
    String name="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vCalorie = inflater.inflate(R.layout.fragment_calorie, container, false);

        frag_calorie = (TextView)vCalorie.findViewById(R.id.frag_calorie);
        frag_calorieGoal = (TextView)vCalorie.findViewById(R.id.frag_caloriegoal);
        frag_calorie_edit = (EditText)vCalorie.findViewById(R.id.frag_edit_calorie);
        frag_calorie_check = (Button)vCalorie.findViewById(R.id.frag_calorie_check);

        ApplicationUser au = new ApplicationUser();
        name = au.getUsername();
        frag_calorie.setText("Hello " + name + " :");

        //get original calorie
        GetRESTResponse getRESTResponse = new GetRESTResponse();
        getRESTResponse.execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.user/User.findByName/" + name);

        frag_calorie_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update calorie
                ApplicationUser au = new ApplicationUser();
                String name = au.getUsername();
                GetRESTResponse getRESTResponse = new GetRESTResponse();
                getRESTResponse.execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.user/User.UpdateByUserACalorie/" + name + "/" +
                        frag_calorie_edit.getText().toString());

                //get currtent time
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                String date1 = sdf.format(date);
                new UpdateCalorie().execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.report/Report.UpdateCalorie/" + name + "/" + frag_calorie_edit.getText().toString()+"/"+date1);

                Toast.makeText(getActivity(), "Edit calorie goal successfully", Toast.LENGTH_SHORT).show();
                frag_calorie_edit.setText("");
            }
        });

        return vCalorie;
    }


    private class UpdateCalorie extends AsyncTask<String, Void, String> {
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


        // Add monsters to database after processing resource
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONArray userContents = new JSONArray(result);
                    //System.out.println("userContents.length() is " + userContents.length());
                    if (userContents.length() == 0) {
                        return;
                    } else {
                        for (int i = 0; i < userContents.length(); i++) {
                            JSONObject userJson = userContents.getJSONObject(i);
                            System.out.println("update calorie successfull " + userJson.getString("calorieGoal"));
                            //get currtent time
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = new Date();
                            String date1 = sdf.format(date);
                            System.out.println("dtadatdta" + date1);

                            ApplicationUser au = new ApplicationUser();
                            new UpdateRemaining().execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.report/Report.UpdateRemaining/" + name + "/" + date1 + "/"+au.getSteps());

                            //frag_calorieGoal.setText("Your calorie goal per day is " + userJson.getString("calorieGoal"));
                            //frag_calorieGoal.setText("Your calorie goal per day is " + userJson.getString("calorieSetGoal"));
                            return;
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private class GetRESTResponse extends AsyncTask<String, Void, String> {
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


        // Add monsters to database after processing resource
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONArray userContents = new JSONArray(result);
                    //System.out.println("userContents.length() is " + userContents.length());
                    if (userContents.length() == 0) {
                        return;
                    } else {
                        for (int i = 0; i < userContents.length(); i++) {
                            JSONObject userJson = userContents.getJSONObject(i);
                            frag_calorieGoal.setText("Your calorie goal per day is  " + userJson.getString("calorieSetGoal"));
                            return;
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
