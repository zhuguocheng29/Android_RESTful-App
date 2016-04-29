package edu.monash.fit5183;

import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import edu.monash.fit5183.Adapter.fragment_diet_adapter;
import edu.monash.fit5183.Adapter.fragment_diet_adapter_bing;
import edu.monash.fit5183.CallServer.UpdateRemaining;
import edu.monash.fit5183.Model.ApplicationUser;

/**
 * Created by Think on 4/19/2016.
 */
public class Diet_fragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private View dView;
    private Spinner frag_diet_list1;
    private Spinner frag_diet_list2;
    private EditText frag_diet_addContent;
    private Button frag_diet_addButton;
    //private TextView frag_diet_textFAT;
    private ListView frag_diet_listView;
    private ImageView frag_diet_img;
    private ListView frag_diet_imgContent;
    private ImageView frag_diet_load;
    private TextView frag_diet_ndbtext;
    private TextView frag_diet_calorie;
    private ProgressBar frag_diet_ndb_progressBar;
    private ProgressBar frag_diet_bing_progressBar;
    private ProgressBar frag_diet_bingImage_progressBar;

    private List<String> arrayitems;
    private ArrayAdapter<String> adapterArea;
    private String list2_items;
    private String list2_items_url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dView = inflater.inflate(R.layout.fragment_diet, container, false);

        frag_diet_list1 = (Spinner) dView.findViewById(R.id.frag_diet_list1);
        frag_diet_list2 = (Spinner) dView.findViewById(R.id.frag_diet_list2);
        frag_diet_addContent = (EditText) dView.findViewById(R.id.frag_diet_addContent);
        frag_diet_addButton = (Button) dView.findViewById(R.id.frag_diet_addButton);
        frag_diet_listView = (ListView) dView.findViewById(R.id.frag_diet_listView);
        frag_diet_img = (ImageView) dView.findViewById(R.id.frag_diet_img);
        frag_diet_imgContent = (ListView) dView.findViewById(R.id.frag_diet_imgContent);
        frag_diet_ndbtext = (TextView)dView.findViewById(R.id.frag_diet_ndbtext);
        frag_diet_ndb_progressBar = (ProgressBar)dView.findViewById(R.id.frag_diet_ndb_progressBar);
        frag_diet_bing_progressBar = (ProgressBar)dView.findViewById(R.id.frag_diet_bing_progressBar);
        frag_diet_bingImage_progressBar = (ProgressBar)dView.findViewById(R.id.frag_diet_bingImage_progressBar);
        frag_diet_calorie = (TextView)dView.findViewById(R.id.frag_diet_calorie);
        frag_diet_load = (ImageView)dView.findViewById(R.id.frag_diet_load);
        //filter bing background
        View v = dView.findViewById(R.id.frag_diet_bingbackground);
        v.getBackground().setAlpha(255);

        frag_diet_calorie.setVisibility(View.INVISIBLE);
        frag_diet_ndb_progressBar.setVisibility(View.INVISIBLE);
        frag_diet_bing_progressBar.setVisibility(View.INVISIBLE);
        frag_diet_bingImage_progressBar.setVisibility(View.INVISIBLE);

        frag_diet_listView.setVisibility(View.INVISIBLE);
        frag_diet_imgContent.setVisibility(View.INVISIBLE);
        frag_diet_load.setVisibility(View.VISIBLE);

        frag_diet_list1.setOnItemSelectedListener(this);
        frag_diet_list2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                frag_diet_calorie.setVisibility(View.INVISIBLE);

                if (frag_diet_list2.getSelectedItem().toString().equals("choose items..."))
                {
                    //return;
                }
                else {
                    list2_items = frag_diet_list2.getSelectedItem().toString();
                    String url="";
                    try {
                        url = URLEncoder.encode(list2_items,"utf-8");
                        url = url.replace("+", "%20");
                        list2_items_url = url;
                        System.out.println("11111  " + list2_items_url);
                        //System.out.println("232323" + url);
                        new GetRESTCalorie().execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.food/Food.findByName/" + url);

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    String partOfitems = list2_items.replace(" ", "%20");
                   // String partofitems_bing = list2_items.replace(" ", "%20");
                    String partofitems_bing = url;
                    if (partOfitems.indexOf("(") != -1) {
                        partOfitems = partOfitems.substring(0, partOfitems.indexOf("("));
                    }
                    if (partOfitems.indexOf(",") != -1) {
                        partOfitems = partOfitems.substring(0, partOfitems.indexOf(","));
                    }
                    frag_diet_listView.setVisibility(View.INVISIBLE);
                    frag_diet_imgContent.setVisibility(View.INVISIBLE);
                    frag_diet_ndbtext.setVisibility(View.INVISIBLE);
                    frag_diet_img.setVisibility(View.INVISIBLE);
                    frag_diet_load.setVisibility(View.INVISIBLE);
                    //filter background
                    View v = dView.findViewById(R.id.frag_diet_bingbackground);
                    v.getBackground().setAlpha(0);

                    frag_diet_ndb_progressBar.setVisibility(View.VISIBLE);
                    //search data from NDB
                    new GetRESTResponseNDB().execute("http://api.nal.usda.gov/ndb/search/?format=json&q=" + partOfitems + "&sort=r&max=1&offset=0&api_key=RPUYD0OXQ9hXtMG5mECvdna9MxuAzgf1zoPXGhmu");

                    //search data from Bing web
                    frag_diet_bing_progressBar.setVisibility(View.VISIBLE);
                    new GetRESTResponseBing().execute("https://api.datamarket.azure.com/Bing/Search/Web?Query=%27" + partofitems_bing + "%27&$top=3&$skip=1&$format=JSON&Market=%27en-GB%27");

                    frag_diet_bingImage_progressBar.setVisibility(View.VISIBLE);
                    //search data from Bing image
                    new GetBingImage().execute("https://api.datamarket.azure.com/Bing/Search/Image?Query=%27"+partofitems_bing+"%27&$top=1&$skip=1&$format=JSON");

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        frag_diet_addButton.setOnClickListener(this);
        return dView;
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getItemAtPosition(position).toString().equals("choose category...")) {
            String[] item = new String[]{"choose items..."};
            adapterArea = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, item);
            frag_diet_list2.setAdapter(adapterArea);
        } else if (parent.getItemAtPosition(position).toString().equals("fruit")) {
            GetRESTResponse getRESTResponse = new GetRESTResponse();
            getRESTResponse.execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.food/Food.findByCategory/fruit");
        }
        else if (parent.getItemAtPosition(position).toString().equals("vegetable")) {
            GetRESTResponse getRESTResponse = new GetRESTResponse();
            getRESTResponse.execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.food/Food.findByCategory/vegetable");
        }
        else if (parent.getItemAtPosition(position).toString().equals("meat")) {
            GetRESTResponse getRESTResponse = new GetRESTResponse();
            getRESTResponse.execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.food/Food.findByCategory/meat");
        }
        else if (parent.getItemAtPosition(position).toString().equals("snack")) {
            GetRESTResponse getRESTResponse = new GetRESTResponse();
            getRESTResponse.execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.food/Food.findByCategory/snack");
        }
        else if (parent.getItemAtPosition(position).toString().equals("others")) {
            GetRESTResponse getRESTResponse = new GetRESTResponse();
            getRESTResponse.execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.food/Food.findByCategory/others");
        }
        else if (parent.getItemAtPosition(position).toString().equals("seafood")) {
            GetRESTResponse getRESTResponse = new GetRESTResponse();
            getRESTResponse.execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.food/Food.findByCategory/seafood");
        }

    }


    //get food's calorie in database
    private class GetRESTCalorie extends AsyncTask<String, Void, String> {
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
            if (result != null) {
                try {
                    JSONArray userContents = new JSONArray(result);
                    if (userContents.length() == 0) {
                        System.out.println("some problems happened");
                        return;
                    } else {
                        for (int i = 0; i < userContents.length(); i++) {
                            JSONObject userJson = userContents.getJSONObject(i);
                            String calorie = userJson.getString("calorie");
                            frag_diet_calorie.setVisibility(View.VISIBLE);
                            frag_diet_calorie.setText("Calorie : " + calorie);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }
        }
    }

    //From Bing to get image
    private class  GetBingImage extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... args) {
            URL url;
            HttpURLConnection conn = null;
            String username = "65196cf1-49f4-44d6-ae1f-3e3bbe518319";
            String password = "crfO+EBYiFbGV6EJUQIiG957gXMq8BzmuU7jnSngllE=";
            String userpass = username+":"+password;
            String basicAuth ="Basic "+new String(Base64.encode(userpass.getBytes(), Base64.NO_WRAP));
            try {
                url = new URL(args[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Authorization",basicAuth);
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
                System.out.println("88888000000");
            } finally {
                conn.disconnect();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject listObject = jsonObject.getJSONObject("d");
                    JSONArray listArray = listObject.getJSONArray("results");
                    JSONObject items_bing = listArray.getJSONObject(0);
                    String mediaUrl = items_bing.getString("MediaUrl");
                    //download image
                    new GetBingImageDownload().execute(mediaUrl);

                    } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                new AlertDialog.Builder(getActivity()).setTitle("Error").setMessage("The request timed out !")
                        .setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }})
                        .setPositiveButton("Cancel",null)
                        .show();
            }
        }
    }


    //From Bing to get information
    private class  GetBingImageDownload extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... args) {
            URL url;
            HttpURLConnection conn = null;
            Bitmap bitmap = null;
            try {
                url = new URL(args[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(20000);
                conn.setDoInput(true);
//                conn.setRequestMethod("GET");
                //System.out.println("3333   " + conn.getResponseCode());
                conn.connect();
                System.out.println("55555   " + conn.getResponseCode());
                if(conn.getResponseCode()==301 ||conn.getResponseCode()==403 || conn.getResponseCode()==404)
                {
                    return null;
                }
                InputStream is = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
                is.close();
                conn.getResponseCode();
                Bitmap b = bitmap;
                return b;

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("000000");
            } finally {
                if(bitmap == null)
                {
                    System.out.println("666666666");
                }
                conn.disconnect();
            }
            return null;
        }

        protected void onPostExecute(Bitmap result) {
            if (result == null)
            {
                frag_diet_bingImage_progressBar.setVisibility(View.INVISIBLE);
                frag_diet_img.setVisibility(View.VISIBLE);
                frag_diet_img.setImageResource(R.drawable.loading_error);
            }
            if (result != null) {
                try {
                    frag_diet_bingImage_progressBar.setVisibility(View.INVISIBLE);
                    frag_diet_img.setVisibility(View.VISIBLE);
                    frag_diet_img.setImageBitmap(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //From Bing to get information
    private class GetRESTResponseBing extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... args) {
            URL url;
            HttpURLConnection conn = null;
            String username = "65196cf1-49f4-44d6-ae1f-3e3bbe518319";
            String password = "crfO+EBYiFbGV6EJUQIiG957gXMq8BzmuU7jnSngllE=";
            String userpass = username+":"+password;
            String basicAuth ="Basic "+new String(Base64.encode(userpass.getBytes(), Base64.NO_WRAP));
            try {
                url = new URL(args[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Authorization",basicAuth);
                String resmsg = "";
                System.out.println("1111  " + conn.getResponseCode());
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
                System.out.println("88888000000");
            } finally {
                conn.disconnect();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject listObject = jsonObject.getJSONObject("d");
                    JSONArray listArray = listObject.getJSONArray("results");
                    List<HashMap<String,String>> item_bing_list = new ArrayList<HashMap<String,String>>();
                    for (int i = 0; i< listArray.length();i++)
                    {
                        HashMap<String,String> hashMap = new HashMap<String,String>();
                        JSONObject items_bing = listArray.getJSONObject(i);

                        System.out.println("8888" + items_bing.getString("Title"));
                        String title = items_bing.getString("Title");
                        int title_position = title.indexOf("...");
                        if (title_position != -1)
                        {
                            title = title.substring(0,title_position-1);
                            if (title.indexOf(",") != -1)
                            {
                                title = title.substring(0,title.lastIndexOf(","));
                                hashMap.put("Title",title);
                            }
                            else if (title.indexOf("-") != -1)
                            {
                                title = title.substring(0,title.lastIndexOf("-"));
                                hashMap.put("Title",title);
                            }
                            else if (title.indexOf("and") != -1)
                            {
                                title = title.substring(0,title.lastIndexOf("and"));
                                hashMap.put("Title",title);
                            }
                            else if (title.indexOf("—") != -1)
                            {
                                title = title.substring(0,title.lastIndexOf("—"));
                                hashMap.put("Title",title);
                            }
                            hashMap.put("Title",title);

                        }
                        else
                        {
                            hashMap.put("Title", items_bing.getString("Title"));
                        }

                        System.out.println("9999" + items_bing.getString("Description"));
                        String original = items_bing.getString("Description");
                        int position = original.indexOf("...");
                        if (position != -1)
                        {

                            System.out.println("22222  " + position);
                            System.out.println("1111 " + original);
                            original = original.substring(0,position);
                            System.out.println("3333 " + original);
                            if(original.indexOf(".") != -1)
                            {
                                original = original.substring(0,original.lastIndexOf(".")+1);
                                hashMap.put("Description",original);
                            }
                            else if (original.indexOf(",") != -1)
                            {
                                original = original.substring(0,original.lastIndexOf(","));
                                hashMap.put("Description",original);
                            }
                            else
                            {
                                hashMap.put("Description",original);
                            }

                        }
                        else {
                            hashMap.put("Description",items_bing.getString("Description"));
                        }
                        //hashMap.put("DisplayUrl",items_bing.getString("DisplayUrl"));
                        item_bing_list.add(hashMap);
                    }

                    frag_diet_bing_progressBar.setVisibility(View.INVISIBLE);
                    frag_diet_imgContent.setVisibility(View.VISIBLE);
                    fragment_diet_adapter_bing adapter = new fragment_diet_adapter_bing(getActivity(),item_bing_list);
                    frag_diet_imgContent.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }
        }
    }

    //From NDB to query ID
    private class GetRESTResponseNDB extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... args) {
            URL url;
            HttpURLConnection conn = null;
            try {
                url = new URL(args[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                String resmsg = "";
                if (conn.getResponseCode() == 404)
                {
                    return null;
                }
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
                System.out.println("8888811111");
            } finally {
                conn.disconnect();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            if (result == null)
            {
                frag_diet_ndb_progressBar.setVisibility(View.INVISIBLE);
                frag_diet_load.setVisibility(View.VISIBLE);
                frag_diet_load.setImageResource(R.drawable.loading_error);

            }

            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject listObject = jsonObject.getJSONObject("list");
                    JSONArray itemObject = listObject.getJSONArray("item");

                    List<String> NDBitems = new ArrayList<String>();

                    if (jsonObject.length() == 0)
                    {
                        //do exception
                        System.out.println("meiyoshujuj!!!");
                        new AlertDialog.Builder(getActivity()).setTitle("Error").setMessage("This food cannot find !")
                                .setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        return;
                                    }})
                                .setPositiveButton("Cancel",null)
                                .show();
                    }
                    else
                    {

                        for (int i = 0 ; i< itemObject.length();i++)
                        {
                            JSONObject object = itemObject.getJSONObject(i);
                            NDBitems.add(object.getString("ndbno"));
                        }
                        for (int j = 0; j < itemObject.length();j++)
                        {
                            new GetRESTResponseNDB_getdata().execute("http://api.nal.usda.gov/ndb/reports/?ndbno="+NDBitems.get(j)+"&type=f&format=json&api_key=RPUYD0OXQ9hXtMG5mECvdna9MxuAzgf1zoPXGhmu");
                        }
                       // new GetRESTResponseNDB_getdata().execute("http://api.nal.usda.gov/ndb/search/?format=json&q=" + partOfitems + "&sort=r&max=2&offset=0&api_key=RPUYD0OXQ9hXtMG5mECvdna9MxuAzgf1zoPXGhmu");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }
        }
    }


    //From NDB to get data from NDB
    private class GetRESTResponseNDB_getdata extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... args) {
            URL url;
            HttpURLConnection conn = null;
            try {
                url = new URL(args[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(30000);
                conn.setConnectTimeout(30000);
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
                //int statusCode = conn.getResponseCode();
                //System.out.println("77777"+statusCode);
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
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject listObject = jsonObject.getJSONObject("report");
                    JSONObject listObject1 = listObject.getJSONObject("food");
                    JSONArray listObject2 = listObject1.getJSONArray("nutrients");

                    List<HashMap<String,String>> NDBitems = new ArrayList<HashMap<String,String>>();
                    if (jsonObject.length() == 0)
                    {
                        //do exception
                        System.out.println("meiyoshujuj!!!");
                        new AlertDialog.Builder(getActivity()).setTitle("Error").setMessage("This food cannot find !")
                                .setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        return;
                                    }})
                                .setPositiveButton("Cancel",null)
                                .show();
                    }
                    else
                    {
                        for (int j = 0; j < 5; j++)
                        {
                            JSONObject jsonObject3 = listObject2.getJSONObject(j);
                            String nutrients_name = jsonObject3.getString("name");
                            //System.out.println("22222" + nutrients_name);
                            JSONArray measures = jsonObject3.getJSONArray("measures");
                            if (measures.length() > 0)
                            {
                                JSONObject object = measures.getJSONObject(0);
                                HashMap<String, String> hashMap = new HashMap<String, String>();

                                hashMap.put("name",nutrients_name);
                                hashMap.put("label", object.getString("label"));
                                hashMap.put("eqv", object.getString("eqv"));
                                hashMap.put("qty", object.getString("qty"));
                                hashMap.put("value", object.getString("value"));

                                NDBitems.add(hashMap);
                            }
                        }
                        frag_diet_ndbtext.setVisibility(View.INVISIBLE);
                        frag_diet_ndb_progressBar.setVisibility(View.INVISIBLE);
                      //  frag_diet_load.setVisibility(View.INVISIBLE);
                        frag_diet_listView.setVisibility(View.VISIBLE);
                        fragment_diet_adapter adapter = new fragment_diet_adapter(getActivity(),NDBitems);
                        frag_diet_listView.setAdapter(adapter);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    int addQuantity = 0;

    //first find quantity table than find diet table
    @Override
    public void onClick(View v) {
        Pattern pattern = Pattern.compile("[0-9]*");
        if (frag_diet_list2.getSelectedItem().toString().equals("choose items...") && frag_diet_list1.getSelectedItem().toString().equals("choose category..."))
        {
            Toast.makeText(getActivity(),"Please input data",Toast.LENGTH_SHORT).show();
        }
        else if(pattern.matcher(frag_diet_addContent.getText().toString()).matches() && !frag_diet_addContent.getText().toString().equals(""))
        {
            addQuantity = Integer.parseInt(frag_diet_addContent.getText().toString());
            GetRESTResponseQuantity getRESTResponseQuantity = new GetRESTResponseQuantity();
            getRESTResponseQuantity.execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.quantity/Quantity.insertQuantity/" + list2_items_url+"/"+ addQuantity);
            frag_diet_list1.setSelection(0);
            frag_diet_list2.setSelection(0);
            frag_diet_addContent.setText("");
        } else if (frag_diet_addContent.getText().toString().equals("") || !pattern.matcher(frag_diet_addContent.getText().toString()).matches()){
            Toast.makeText(getActivity(),"Please input correct format", Toast.LENGTH_SHORT).show();
            frag_diet_addContent.setText("");
        }



    }

    //insert data to quantity table
    private class GetRESTResponseQuantity extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... args) {
            URL url;
            HttpURLConnection conn = null;
            try {
                url = new URL(args[0].replace(" ","%20"));
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
                //try to get response
                int status = conn.getResponseCode();
                //System.out.println("status is" + status);
                return status+"";
             //   return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
            return null;
        }

        // Add data to database after processing resource
        protected void onPostExecute(String result) {
            if (result.equals("200"))
            {
                //insert quantity successfully than insert diet
                ApplicationUser user = new ApplicationUser();
                user.getUsername();
                GetRESTResponseDiet getRESTResponseDiet = new GetRESTResponseDiet();
                getRESTResponseDiet.execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.diet/Diet.insertDiet/" + user.getUsername() + "/" + list2_items_url + "/" + addQuantity);
            }
        }
    }

    //insert data to diet table after inserting quantity table
    private class GetRESTResponseDiet extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... args) {
            URL url;
            HttpURLConnection conn = null;
            try {
                url = new URL(args[0].replace(" ","%20"));
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
                //try to get response
                int status = conn.getResponseCode();
                //System.out.println("status is" + status);
                return status+"";
                //   return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
            return null;
        }

        // Add monsters to database after processing resource
        protected void onPostExecute(String result) {
            if (result.equals("200"))
            {
                Toast.makeText(getActivity(),"insert successful",Toast.LENGTH_SHORT).show();

                ApplicationUser au = new ApplicationUser();
                String username = au.getUsername();
                //get currtent time
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                String date1 = sdf.format(date);
                System.out.println("date1=====" + date1);
                new UpdatefoodCalorie().execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.report/Report.UpdateFoodCalorie/" + username + "/" + date1);
            }
        }
    }

    private class UpdatefoodCalorie extends AsyncTask<String, Void, String> {
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
                    //have record before
                    if(result.equals("1"))
                    {
                        System.out.println("update food successful");
                        //get currtent time
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = new Date();
                        String date1 = sdf.format(date);
                        System.out.println("dtadatdta" + date1);

                        ApplicationUser au = new ApplicationUser();
                        String name = au.getUsername();
                        new UpdateRemaining().execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.report/Report.UpdateRemaining/" + name + "/" + date1 + "/"+au.getSteps());

                    }
                    else
                    {
                        System.out.println("update food wrong" );
                        //new GetUser().execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.user/User.findByName/"+userName);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("wrong");
            }
        }
    }

    //by list1 get list2 data
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
            if (result != null) {
                try {
                    JSONArray userContents = new JSONArray(result);
                    //System.out.println("userContents.length() is " + userContents.length());
                    if (userContents.length() == 0) {
                        System.out.println("some problems happened");
                        return;
                    } else {
                        arrayitems = new ArrayList<String>();
                        arrayitems.add("choose items...");
                        for (int i = 0; i < userContents.length(); i++) {
                            JSONObject userJson = userContents.getJSONObject(i);
                            arrayitems.add(userJson.getString("name"));
                        }
                        //System.out.println(arrayitems.toString());
                        adapterArea = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, arrayitems);
                        frag_diet_list2.setAdapter(adapterArea);
                       // System.out.println("9876542");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }
        }
    }
}