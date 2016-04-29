package edu.monash.fit5183;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import edu.monash.fit5183.CallServer.UpdateRemaining;
import edu.monash.fit5183.CallServer.UpdateTotalCalorie;
import edu.monash.fit5183.Model.ApplicationUser;
import edu.monash.fit5183.Model.Step;
import edu.monash.fit5183.Util.DatabaseHelper;
import edu.monash.fit5183.Util.DatePickerDialog;

/**
 * Created by Think on 4/16/2016.
 */
public class Steps_fragment extends Fragment implements View.OnClickListener {
    View vsteps;
    private EditText frag_steps;
    private Button frag_steps_check;
    private ListView frag_listView;
    private ListView frag_listView_title;
    private Button frag_steps_date;
    private TextView frag_steps_dateshow;
    private TextView frag_steps_totalSteps;
    DatabaseHelper db;

    private int steps = 0;
    private String date1 = "";
    protected SimpleAdapter ListAdapter1;
    protected SimpleAdapter ListAdapter_title;
    final String[] text = {""};
    ApplicationUser au = new ApplicationUser();
    protected List<HashMap<String, String>> ListArray1 = new ArrayList<HashMap<String, String>>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vsteps = inflater.inflate(R.layout.fragment_steps, container, false);

        frag_steps = (EditText) vsteps.findViewById(R.id.frag_steps);
        frag_steps_check = (Button) vsteps.findViewById(R.id.frag_steps_check);
        frag_listView = (ListView) vsteps.findViewById(R.id.frag_steps_listView);
        frag_listView_title = (ListView) vsteps.findViewById(R.id.frag_steps_listView2);
        frag_steps_date = (Button) vsteps.findViewById(R.id.frag_steps_date);
        frag_steps_dateshow = (TextView) vsteps.findViewById(R.id.frag_steps_dateshow);
        frag_steps_totalSteps = (TextView) vsteps.findViewById(R.id.frag_steps_totalSteps);

        //change 4.20
        db = new DatabaseHelper(getActivity());
        String name = au.getUsername();

        //HashMap<String,String> map1 = new HashMap<String,String>();
        String[] colHEAD = new String[]{"Steps", "DateTime"};
        int[] frag_col = new int[]{R.id.frag_steps_steps, R.id.frag_steps_date};

        //add title
        HashMap<String, String> map_title = new HashMap<String, String>();
        List<HashMap<String, String>> ListArray_title = new ArrayList<HashMap<String, String>>();
        map_title.put("Steps", "Steps");
        map_title.put("DateTime", "DateTime");
        ListArray_title.add(map_title);
        ListAdapter_title = new SimpleAdapter(getActivity(), ListArray_title, R.layout.fragment_steps_listview, colHEAD, frag_col);
        frag_listView_title.setAdapter(ListAdapter_title);

        //show steps data
        ListArray1 = db.getStepRecord(au.getUsername());
        ListAdapter1 = new SimpleAdapter(getActivity(), ListArray1, R.layout.fragment_steps_listview, colHEAD, frag_col);
        frag_listView.setAdapter(ListAdapter1);


        //get steps data
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String date1 = sdf.format(date);
        int total_steps = db.queryDaySteps(au.getUsername(), date1);
        System.out.println("total steps is00000 " + total_steps);

        //listenser

        frag_steps_check.setOnClickListener(this);
        frag_steps_date.setOnClickListener(new View.OnClickListener() {
            Calendar c = Calendar.getInstance();
            String textString2 = "";
            //int total_steps;
            @Override
            public void onClick(View v) {

                new DatePickerDialog(getActivity(), 0, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                          int startDayOfMonth) throws ParseException {
                        String textString = String.format("Date is :%d-%d-%d\n", startYear,
                                startMonthOfYear + 1, startDayOfMonth);
                        textString2 = String.format("%d-%d-%d", startYear, startMonthOfYear + 1, startDayOfMonth);
                        text[0] = textString2;
                        //System.out.println(textString2);
                        frag_steps_dateshow.setText(textString);
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        String date2 = "";
                        Date date = df.parse(textString2);
                        date2 = df.format(date);
                        int total_steps = 0;
                        total_steps = db.queryDaySteps(au.getUsername(), date2);
                        if (total_steps == 0)
                        {
                            System.out.println("66665555");
                            new GetSteps().execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.report/Report.findByTotalStepsByusername/"+au.getUsername()+"/"+date2);
                        }
                        else {
                            frag_steps_totalSteps.setText("Total steps are :" + total_steps +"");
                            //add title
                            String[] colHEAD = new String[]{"Steps", "DateTime"};
                            int[] frag_col = new int[]{R.id.frag_steps_steps, R.id.frag_steps_date};
                            HashMap<String, String> map_title = new HashMap<String, String>();
                            List<HashMap<String, String>> ListArray_title = new ArrayList<HashMap<String, String>>();
                            map_title.put("Steps", "Steps");
                            map_title.put("DateTime", "DateTime");
                            ListArray_title.add(map_title);
                            ListAdapter_title = new SimpleAdapter(getActivity(), ListArray_title, R.layout.fragment_steps_listview, colHEAD, frag_col);
                            frag_listView_title.setAdapter(ListAdapter_title);
                            frag_listView.setVisibility(View.INVISIBLE);
                            //add data
                            DatabaseHelper db = new DatabaseHelper(getActivity());
                            ListArray1 = db.getStepRecordDate(au.getUsername(),date2);
    //                        ListArray1 = db.getStepRecord(au.getUsername());
                            ListAdapter1 = new SimpleAdapter(getActivity(), ListArray1, R.layout.fragment_steps_listview, colHEAD, frag_col);
                            frag_listView.setVisibility(View.VISIBLE);
                            frag_listView.setAdapter(ListAdapter1);

                        }
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE)).show();
               // System.out.println("12345" + total_steps);
            }
        });

        return vsteps;
    }





    private class GetSteps extends AsyncTask<String, Void, String> {
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
            if (result != null && !result.equals("1")) {
                try {
                    frag_steps_totalSteps.setText("Total steps are :" + result+"");

                  /*  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String date2 = "";
                    Date date = df.parse(text[0]);
                    date2 = df.format(date);*/
                    //add title
                    String[] colHEAD = new String[]{"Steps", "DateTime"};
                    int[] frag_col = new int[]{R.id.frag_steps_steps, R.id.frag_steps_date};
                    HashMap<String, String> map_title = new HashMap<String, String>();
                    List<HashMap<String, String>> ListArray_title = new ArrayList<HashMap<String, String>>();
                    map_title.put("Steps", "Steps");
                    map_title.put("DateTime", "DateTime");
                    ListArray_title.add(map_title);
                    ListAdapter_title = new SimpleAdapter(getActivity(), ListArray_title, R.layout.fragment_steps_listview, colHEAD, frag_col);
                    frag_listView_title.setAdapter(ListAdapter_title);
                    frag_listView.setVisibility(View.INVISIBLE);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else
            {
                frag_steps_totalSteps.setText("Sorry, you don't have any records");
                //add title
                String[] colHEAD = new String[]{"Steps", "DateTime"};
                int[] frag_col = new int[]{R.id.frag_steps_steps, R.id.frag_steps_date};
                HashMap<String, String> map_title = new HashMap<String, String>();
                List<HashMap<String, String>> ListArray_title = new ArrayList<HashMap<String, String>>();
                map_title.put("Steps", "Steps");
                map_title.put("DateTime", "DateTime");
                ListArray_title.add(map_title);
                ListAdapter_title = new SimpleAdapter(getActivity(), ListArray_title, R.layout.fragment_steps_listview, colHEAD, frag_col);
                frag_listView_title.setAdapter(ListAdapter_title);
                frag_listView.setVisibility(View.INVISIBLE);
            }
        }
    }

   // int total_step_rest = 0;

    @Override
    public void onClick(View v) {

        HashMap<String, String> map_title = new HashMap<String, String>();

        Pattern pattern = Pattern.compile("[0-9]*");
        if (pattern.matcher(frag_steps.getText().toString()).matches() && !frag_steps.getText().toString().equals("")) {
            int steps = Integer.parseInt(frag_steps.getText().toString());
            //get currtent time
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            String date1 = sdf.format(date);
            Step step = new Step(db.getUserId(au.getUsername()), steps, date1);
            db.addStep(step);


            //get currtent time
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            Date date_1 = new Date();
            String date2 = sdf1.format(date_1);
            //update server datebase
            DatabaseHelper db = new DatabaseHelper(getActivity());
            int total_step_rest = db.queryDaySteps(au.getUsername(),date2);
            //total_step_rest+=steps;
            //System.out.println("44444  " + total_step_rest);
            new UpdateStepsAStepsCalorie().execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.report/Report.UpdateStepsAStepsCalorie/"+au.getUsername()+"/"+total_step_rest+"/"+date2);

            String[] colHEAD = new String[]{"Steps", "DateTime"};
            int[] frag_col = new int[]{R.id.frag_steps_steps, R.id.frag_steps_date};
            //add title
            List<HashMap<String, String>> ListArray_title = new ArrayList<HashMap<String, String>>();
            map_title.put("Steps", "Steps");
            map_title.put("DateTime", "DateTime");
            ListArray_title.add(map_title);
            ListAdapter_title = new SimpleAdapter(getActivity(), ListArray_title, R.layout.fragment_steps_listview, colHEAD, frag_col);
            frag_listView_title.setAdapter(ListAdapter_title);
            //add data

            ListArray1 = db.getStepRecord(au.getUsername());
            ListAdapter1 = new SimpleAdapter(getActivity(), ListArray1, R.layout.fragment_steps_listview, colHEAD, frag_col);
            frag_listView.setAdapter(ListAdapter1);
            frag_listView.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "add data successfully !", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getActivity(), "Please input correct data format", Toast.LENGTH_SHORT).show();
            frag_steps.setError("Please input correct data format");
            return;
        }

        frag_steps.setText("");
    }

    private class UpdateStepsAStepsCalorie extends AsyncTask<String, Void, String> {
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
            if (result != null && result.equals("1")) {
                try {
                    System.out.println("update successful");
                    //get currtent time
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                    Date date_1 = new Date();
                    String date2 = sdf1.format(date_1);
                    //update server datebase
                    DatabaseHelper db = new DatabaseHelper(getActivity());
                    int total_step_rest = db.queryDaySteps(au.getUsername(),date2);

                    //update totalCalorie
                    new UpdateTotalCalorie().execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.report/Report.UpdateburnedCalorie/"+ au.getUsername()+"/"+total_step_rest+"/"+date2);

                    //update remaining
                    new UpdateRemaining().execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.report/Report.UpdateRemaining/" + au.getUsername() + "/" + date2 + "/" + total_step_rest);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else
            {
                System.out.println("update error");
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //ListArray1 = new ArrayList<HashMap<String, String>>();

    }
}
