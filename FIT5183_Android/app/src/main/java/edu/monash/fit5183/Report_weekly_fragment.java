package edu.monash.fit5183;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.json.JSONArray;
import org.json.JSONObject;

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

import edu.monash.fit5183.Model.ApplicationUser;
import edu.monash.fit5183.Model.Records;
import edu.monash.fit5183.Util.DatePickerDialog;

/**
 * Created by Think on 4/26/2016.
 */
public class Report_weekly_fragment extends Fragment {
    View vreport_weekly;
    private TextView frag_report_start;
    private TextView frag_report_end;
    private BarChart mChart;
    private Typeface tf;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vreport_weekly = inflater.inflate(R.layout.fragment_report_weekly, container, false);
        frag_report_start = (TextView)vreport_weekly.findViewById(R.id.frag_report_start);
        frag_report_end = (TextView)vreport_weekly.findViewById(R.id.frag_report_end);
        mChart = (BarChart)vreport_weekly.findViewById(R.id.period_chart);

        ApplicationUser au = new ApplicationUser();
        final String username = au.getUsername();

        final String[] dateStart = {""};
        final String[] dateEnd = {""};

        frag_report_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                final String[] textString2 = {""};
                new DatePickerDialog(getActivity(), 0, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                          int startDayOfMonth) throws ParseException {
                        String textString = String.format("Date is :%d-%d-%d\n", startYear, startMonthOfYear + 1, startDayOfMonth);
                        // System.out.println("11111  " + textString);
                        textString2[0] = String.format("%d-%d-%d", startYear, startMonthOfYear + 1, startDayOfMonth);
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        String date2 = "";
                        Date date = df.parse(textString2[0]);
                        date2 = df.format(date);
                        dateStart[0] = date2;
                        frag_report_start.setText(" ----      "+date2+"      ----");
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE)).show();
            }
        });


        frag_report_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                final String[] textString2 = {""};
                new DatePickerDialog(getActivity(), 0, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                          int startDayOfMonth) throws ParseException {
                        String textString = String.format("Date is :%d-%d-%d\n", startYear, startMonthOfYear + 1, startDayOfMonth);
                        // System.out.println("11111  " + textString);
                        textString2[0] = String.format("%d-%d-%d", startYear, startMonthOfYear + 1, startDayOfMonth);
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        String date2 = "";
                        Date date = df.parse(textString2[0]);
                        date2 = df.format(date);
                        dateEnd[0] = date2;
                        frag_report_end.setText(" ----      "+date2+"      ----");

                        if (dateStart[0].equals(""))
                        {
                            Toast.makeText(getActivity(),"Please input startDate ",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            int result = dateStart[0].compareTo(dateEnd[0]);

                            if (result > 0)
                            {
                                Toast.makeText(getActivity(),"Please input date period properly !",Toast.LENGTH_SHORT).show();
                                mChart.clear();
                            }
                            else
                            {
                                new GetRangeRecord().execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.report/Report.findRangeRecords/" + username+"/"+dateStart[0]+"/"+date2);

                            }
                           // System.out.println("2222299999  " + dateStart[0]);
                        }

                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE)).show();
            }
        });




        return vreport_weekly;
    }


    private class GetRangeRecord extends AsyncTask<String, Void, String> {
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
            if (!result.equals("") && !result.equals("[]")) {
                try {
                    System.out.println("66666   " + result);
                    JSONArray jsonArray = new JSONArray(result);
                    List<Records> list = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        Records records = new Records();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        float foodCalorie = (float)jsonObject.getDouble("foodCalorie");
                        float burned = (float)jsonObject.getDouble("burned");
                        float remaining = (float)jsonObject.getDouble("remaining");
                        float goal = (float)jsonObject.getDouble("goal");
                        String date1 = jsonObject.getString("date");

                        records.setFoodCalorie(foodCalorie);
                        records.setBurned(burned);
                        records.setRemaining(remaining);
                        records.setGoal(goal);
                        records.setDate(date1);
                        list.add(records);
                    }
                    graph(list);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("get RangeReport wrong");
            }
        }
    }


    private void graph( List<Records> list) {
        mChart.clear();
        mChart.setDescription("");
        mChart.setPinchZoom(false);
        mChart.setDrawBarShadow(false);
        mChart.setDrawGridBackground(false);
        tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);
        l.setTypeface(tf);
        l.setYOffset(0f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);

        XAxis xl = mChart.getXAxis();
        xl.setTypeface(tf);
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(tf);
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(30f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        mChart.getAxisRight().setEnabled(false);


        //paint graph
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            xVals.add(list.get(i).getDate());
        }
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals3 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals4 = new ArrayList<BarEntry>();

        for (int i = 0; i < list.size(); i++) {
           // System.out.println("55555  " + list.get(i).getGoal());
            float val = list.get(i).getGoal();
            //System.out.println("66666  " + val);
            yVals1.add(new BarEntry(val, i));
        }
        for (int i = 0; i < list.size(); i++) {
            float val = list.get(i).getBurned();
            yVals2.add(new BarEntry(val, i));
        }
        for (int i = 0; i < list.size(); i++) {
           // System.out.println("55555  " + list.get(i).getFoodCalorie());
            float val = list.get(i).getFoodCalorie();
           // System.out.println("66666  " + val);
            yVals3.add(new BarEntry(val, i));
        }
        for (int i = 0; i < list.size(); i++) {
            float val = list.get(i).getRemaining();
            yVals4.add(new BarEntry(val, i));
        }


        BarDataSet set1,set2,set3,set4;
        set1 = new BarDataSet(yVals1, "Calorie goal");
        set1.setColor(Color.rgb(104, 241, 175));
        set2 = new BarDataSet(yVals2, "Burned calorie");
        set2.setColor(Color.rgb(41, 129, 188));
        set3 = new BarDataSet(yVals3, "Consumed calorie");
        set3.setColor(Color.rgb(255,142,155));
        set4 = new BarDataSet(yVals4, "Remaining calorie");
        set4.setColor(Color.rgb(255,210,140));

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);
        dataSets.add(set3);
        dataSets.add(set4);
//        //cancel data show
//        set1.setDrawValues(false);
//        set2.setDrawValues(false);
        BarData data = new BarData(xVals, dataSets);
        data.setGroupSpace(80f);
        data.setValueTypeface(tf);
        mChart.setData(data);
        mChart.invalidate();

        //paint graph

    }


}

