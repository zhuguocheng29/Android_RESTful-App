package edu.monash.fit5183;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.nfc.NfcAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

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
import edu.monash.fit5183.Util.DatabaseHelper;
import edu.monash.fit5183.Util.DatePickerDialog;

/**
 * Created by Think on 4/26/2016.
 */
public class Report_daily_fragment extends Fragment implements View.OnClickListener {
    View vreport_daily;
    private TextView frag_report_today;
    private TextView frag_report_next;
    private TextView frag_report_daily_no;
    private BarChart mChart;
    private PieChart chart2;
    private GraphView graph;
    private GraphView graph2;
    private String username;
    private Typeface tf;
    ArrayList<Integer> arrayList_graph1;
    ArrayList<Double> arrayList_graph2;
    List<Double> array_graph3;
    List<Float> array_graph4;
    int comp = 1;
    int a = 1;
    int c;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vreport_daily = inflater.inflate(R.layout.fragment_report_daily, container, false);
        frag_report_today = (TextView) vreport_daily.findViewById(R.id.frag_report_today);
        frag_report_next = (TextView) vreport_daily.findViewById(R.id.frag_report_next);
        frag_report_daily_no = (TextView) vreport_daily.findViewById(R.id.frag_report_daily_no);

        graph = (GraphView) vreport_daily.findViewById(R.id.graph);
        graph2 = (GraphView) vreport_daily.findViewById(R.id.graph2);
        mChart = (BarChart) vreport_daily.findViewById(R.id.chart1);
        chart2 = (PieChart) vreport_daily.findViewById(R.id.chart2);


        ApplicationUser au = new ApplicationUser();
        username = au.getUsername();
        //default date
        //get currtent time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String date1 = sdf.format(date);
        DatabaseHelper db = new DatabaseHelper(getActivity());
        frag_report_daily_no.setVisibility(View.INVISIBLE);
        if (db.queryDayExist(username, date1) == true) {
            try {
                DailyGraph(date1);
                //4.28
                graph.setVisibility(View.VISIBLE);
                graph2.setVisibility(View.VISIBLE);
                mChart.setVisibility(View.INVISIBLE);
                chart2.setVisibility(View.INVISIBLE);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("database don't have any records");
            frag_report_daily_no.setVisibility(View.VISIBLE);
        }
        graph.setVisibility(View.VISIBLE);
        graph2.setVisibility(View.VISIBLE);
        mChart.setVisibility(View.INVISIBLE);
        chart2.setVisibility(View.INVISIBLE);

//        frag_report_daily_no2.setVisibility(View.INVISIBLE);

        frag_report_today.setOnClickListener(this);

        frag_report_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (frag_report_next.getText().equals("Next"))
               {

                   graph.setVisibility(View.INVISIBLE);
                   graph2.setVisibility(View.INVISIBLE);
                   mChart.setVisibility(View.VISIBLE);
                   chart2.setVisibility(View.VISIBLE);
                   frag_report_next.setText("Back");
               }
               else if (frag_report_next.getText().equals("Back"))
                {

                    graph.setVisibility(View.VISIBLE);
                    graph2.setVisibility(View.VISIBLE);
                    mChart.setVisibility(View.INVISIBLE);
                    chart2.setVisibility(View.INVISIBLE);
                    frag_report_next.setText("Next");
                }
            }
        });

        return vreport_daily;
    }


    private void DailyGraph(String date_daily) throws ParseException {
        graph1(date_daily);
        graph2(date_daily);
        graph4(date_daily);

    }


    private void graph1(String date_time) {
//        //4.28
//        mChart.setVisibility(View.INVISIBLE);
//        graph.setVisibility(View.VISIBLE);

        graph.removeAllSeries();
        a = 1;
//        frag_report_daily_no.setVisibility(View.INVISIBLE);
        graph.setTitle(date_time+"'s steps figure  ");
        DatabaseHelper db = new DatabaseHelper(getActivity());
//        //get currtent time
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = new Date();
//        String date1 = sdf.format(date);
        String date1 = date_time;

        List<HashMap<String, Integer>> listSteps = db.getStepsPeriod(username, date1);
        List<Integer> stepslist = new ArrayList<Integer>();
        List<Integer> timelist = new ArrayList<Integer>();
        int period_steps = 0;

        if (listSteps == null)
        {
            System.out.println("777777777777");
//            //4.28
//            if (frag_report_next.getText().equals("Next"))
//            {
//               // frag_report_daily_no.setVisibility(View.VISIBLE);
//                graph.setVisibility(View.INVISIBLE);
//            }
//            else
//            {
//               // frag_report_daily_no.setVisibility(View.INVISIBLE);
//                graph.setVisibility(View.INVISIBLE);
//            }

            //4.28frag_report_daily_no.setVisibility(View.VISIBLE);
            //4.28graph.setVisibility(View.INVISIBLE);
            a = 0;
            arrayList_graph1.clear();
        }
        else
        {
            //4.28
            if (frag_report_next.getText().equals("Next"))
            {
                graph.setVisibility(View.VISIBLE);
            }
            else
            {
                graph.setVisibility(View.INVISIBLE);
            }
           //4.28 graph.setVisibility(View.VISIBLE);
            for (int i = 0; i < listSteps.size(); i++) {
                stepslist.add(listSteps.get(i).get("Steps"));
                timelist.add(listSteps.get(i).get("DateTime"));

            }
            //System.out.println("66666" + timelist.toString());
            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
            arrayList_graph1 = new ArrayList<>();
            if (stepslist.size() != 0) {
                for (int j = 0; j < stepslist.size(); j++) {
                    int x = timelist.get(j);
                    int y = stepslist.get(j);
                    arrayList_graph1.add(y);
                    series.appendData(new DataPoint(x, y), false, stepslist.size());
                }
            }
            graph.addSeries(series);
            // Set legend
            series.setTitle("steps");
//        StaticLabelsFormatter formatter = new StaticLabelsFormatter(graph);
//        formatter.setHorizontalLabels(new String[]{
//                "0", "1", "12", "18", "24"
//        });
//        graph.getGridLabelRenderer().setLabelFormatter(formatter);
            graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setMinX(0);
            graph.getViewport().setMaxX(24);
            graph.getLegendRenderer().setMargin(1);
            graph.getGridLabelRenderer().setHorizontalAxisTitle("Hours");
            //Set Line colour
            series.setColor(Color.rgb(255,0,0));
            //Set Data point on the line graph
            series.setDrawDataPoints(true);
            series.setDataPointsRadius(4);
            graph.getLegendRenderer().setVisible(true);
            graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        }

    }


    private void graph2(String date_time) {
//        //get currtent time
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = new Date();
//        String date1 = sdf.format(date);
        String date1 = date_time;
        new GetRESTFood().execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.report/Report.CalFoodCaloriePeriod/" + username + "/" + date1);

    }

    //get food's period calorie in database
    private class GetRESTFood extends AsyncTask<String, Void, String> {
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
                buffer.close();
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
            System.out.println("88888 " + result);
            if (result != null) {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    if (jsonArray.length() == 0) {
                        System.out.println("some problems happened");
                        return;
                    } else {
                        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
                        arrayList_graph2 = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {

                            int x = jsonArray.getJSONObject(i).getInt("dateTime");
                            double y = jsonArray.getJSONObject(i).getDouble("food");
                            arrayList_graph2.add(y);
                            series.appendData(new DataPoint(x, y), false, jsonArray.length());
                        }
                        //4.28
                        if (frag_report_next.getText().equals("Next"))
                        {
                            graph2.setVisibility(View.VISIBLE);

                        }
                        else
                        {
                            graph2.setVisibility(View.INVISIBLE);
                        }

                        //4.28  graph2.setVisibility(View.VISIBLE);
                        graph2.removeAllSeries();
                        graph2.setTitle("Food consumed calorie figure  ");
                        graph2.addSeries(series);
                        // Set legend
                        series.setTitle("Food calorie");
                        graph2.getViewport().setXAxisBoundsManual(true);
                        graph2.getViewport().setMinX(0);
                        graph2.getViewport().setMaxX(24);
                        graph2.getLegendRenderer().setMargin(1);
                        graph2.getGridLabelRenderer().setHorizontalAxisTitle("Hours");
                        //Set Line colour
                        series.setColor(Color.rgb(41, 129, 188));
                        //Set Data point on the line graph
                        series.setDrawDataPoints(true);
                        series.setDataPointsRadius(4);
                        graph2.getLegendRenderer().setVisible(true);
                        graph2.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

                        new GetUser().execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.user/User.findByName/" + username);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }
        }
    }


    private class GetUser extends AsyncTask<String, Void, String> {
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
                    JSONArray jsonArray = new JSONArray(result);
                    if (jsonArray.length() != 0) {
                        float weight;
                        int stepPerMile;
                        float ratio = 0f;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            weight = (float) jsonObject.getDouble("weight");
                            stepPerMile = jsonObject.getInt("stepPerMile");
                            ratio = (float) (weight * 0.49 / stepPerMile);
                        }
                       // array_graph3.clear();
                        array_graph3 = new ArrayList<>();
                        if (arrayList_graph1.size()!=0)
                        {
                            for (int j = 0; j < 24; j++) {
                                //System.out.println("array_graph_1    " + arrayList_graph1.get(j));
                                //get steps calorie
                                array_graph3.add((double) (arrayList_graph1.get(j) * ratio));
                            }
                            // graph.setVisibility(View.INVISIBLE);
                            graph3();
                        }
                        else
                        {
                            c = 1;
                            array_graph3 = new ArrayList<>();
                            graph3();
                           // mChart.setVisibility(View.INVISIBLE);
                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("fault!!!");
            }
        }
    }

    private void graph4(String date_g4)
    {
        //get currtent time
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = new Date();
//        String date1 = sdf.format(date);
        String date1 = date_g4;
        new GetRecord().execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.report/Report.findByUsernameAReportDate/" + username + "/" + date1);

    }

    private class GetRecord extends AsyncTask<String, Void, String> {
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
                    System.out.println("2222   " + result);

                    JSONArray jsonArray = new JSONArray(result);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    float burned = (float)jsonObject.getDouble("totalCalorieConsumed");
                    float food_calorie = (float)jsonObject.getDouble("totalFoodCalorie");
                    float remaining = Math.abs((float)jsonObject.getDouble("remaining"));

                    array_graph4 = new ArrayList<>();
                    array_graph4.add(burned);
                    array_graph4.add(food_calorie);
                    //array_graph4.add(remaining);

                    //4.28
                    chart2.removeAllViews();
                    chart2.clear();

                    graph4_data();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("get report wrong");
             //   graph2.setVisibility(View.INVISIBLE);
                array_graph4 = new ArrayList<>();
                graph4_data();
                //chart2.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void graph4_data()
    {
        //graph2.setVisibility(View.INVISIBLE);
        //4.28
        chart2.removeAllViews();
        chart2.clear();

        if (frag_report_next.getText().equals("Next"))
        {
            chart2.setVisibility(View.INVISIBLE);
        }
        else
        {
            chart2.setVisibility(View.VISIBLE);
        }

        chart2.setUsePercentValues(true);
        chart2.setDescription("");
        chart2.setExtraOffsets(5, 10, 5, 5);

        chart2.setDragDecelerationFrictionCoef(0.95f);
        tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        chart2.setCenterTextTypeface(Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf"));
        chart2.setCenterText("Calorie percent ");
        chart2.setCenterTextSize(15f);
        chart2.setDrawHoleEnabled(true);
        chart2.setHoleColor(Color.WHITE);
        chart2.setTransparentCircleColor(Color.WHITE);
        chart2.setTransparentCircleAlpha(110);
        chart2.setHoleRadius(58f);
        chart2.setTransparentCircleRadius(61f);
        chart2.setDrawCenterText(true);
        chart2.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart2.setRotationEnabled(true);
        chart2.setHighlightPerTapEnabled(true);
        chart2.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);
        Legend l = chart2.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        if (array_graph4.size()!=0)
        {
            for (int i = 0; i < 2; i++) {
                yVals1.add(new Entry(array_graph4.get(i),i));
            }

            ArrayList<String> xVals = new ArrayList<String>();
            String[] member = new String[]{"Burned","Consumed"};

            for (int i = 0; i < 2; i++)
                xVals.add(member[i]);

            PieDataSet dataSet = new PieDataSet(yVals1, "Calorie Comparison");
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(5f);
            dataSet.setColors(ColorTemplate.PASTEL_COLORS);

            PieData data = new PieData(xVals, dataSet);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(11f);
            data.setValueTextColor(Color.BLACK);
            data.setValueTypeface(tf);
            chart2.setData(data);
            // undo all highlights
            chart2.highlightValues(null);

            chart2.invalidate();
            //4.28
            array_graph4.clear();

        }
        else
        {
            //yVals1.add(null);
        }
    }


    private void graph3() {
//        //4.28
//        graph.setVisibility(View.INVISIBLE);
//        frag_report_daily_no.setVisibility(View.INVISIBLE);
//        mChart.setVisibility(View.VISIBLE);
        if (frag_report_next.getText().equals("Next"))
        {
            mChart.setVisibility(View.INVISIBLE);
        }
        else
        {
            mChart.setVisibility(View.VISIBLE);
        }

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
        for (int i = 0; i < 24; i++) {
            xVals.add((i) + "");
        }
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();

        if (array_graph3.size() != 0)
        {
            for (int i = 0; i < 24; i++) {
                //System.out.println("55555  " + array_graph3.get(i));
                float val = Float.parseFloat(array_graph3.get(i) + "");
                // System.out.println("66666  " + val);
                yVals1.add(new BarEntry(val, i));
            }
            for (int i = 0; i < 24; i++) {
                float val = Float.parseFloat(arrayList_graph2.get(i) + "");
                yVals2.add(new BarEntry(val, i));
            }

            BarDataSet set1, set2;
            set1 = new BarDataSet(yVals1, "Steps calorie");
            set1.setColor(Color.rgb(255,210,140));
            set2 = new BarDataSet(yVals2, "Food calorie");
            set2.setColor(Color.rgb(164, 228, 251));
            //rgb(104, 241, 175),rgb(164, 228, 251)
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            dataSets.add(set2);
            //cancel data show
            set1.setDrawValues(false);
            set2.setDrawValues(false);
            BarData data = new BarData(xVals, dataSets);
            data.setGroupSpace(80f);
            data.setValueTypeface(tf);
            mChart.setData(data);
            mChart.invalidate();
        }



        //paint graph

    }



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
                    graph1(date2);
                    graph2(date2);
                    graph4(date2);
                    //date_graph3 = date2;
                    frag_report_today.setText(" ----      "+date2+"      ----");
                    //4.28
                    if (frag_report_next.getText().equals("Back"))
                    {
                        System.out.println("BACKBACK");
                        graph.setVisibility(View.INVISIBLE);
                        graph2.setVisibility(View.INVISIBLE);
                    }

                }
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE)).show();

    }
}
