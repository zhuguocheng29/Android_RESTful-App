package edu.monash.fit5183;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.monash.fit5183.CallServer.UpdateRemaining;
import edu.monash.fit5183.CallServer.UpdateTotalCalorie;
import edu.monash.fit5183.Model.ApplicationUser;
import edu.monash.fit5183.Model.User;
import edu.monash.fit5183.Util.CyptoUtils;
import edu.monash.fit5183.Util.DatabaseHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    private EditText username;
    private EditText password;
    private TextView forget;
    private Button signIn;
    private Button signUp;
   // private DatabaseHelper dbHelper;
    String userName = "";
    String passWord = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        forget = (TextView) findViewById(R.id.forget);
        signIn = (Button) findViewById(R.id.signIn);
        signUp = (Button) findViewById(R.id.signUp);

        //transmit steps data
        //ApplicationUser.setListArray(new ArrayList<HashMap<String, String>>());
        // Get our database helper
      //  dbHelper = new DatabaseHelper(getApplicationContext());

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(),"Sorry I can't help you",Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(MainActivity.this).setMessage("Sorry I can't help you! ")
                        .setNegativeButton("Yes", null)
                        .show();
            }
        });

        signIn.setOnClickListener(this);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, signUp.class);
                startActivityForResult(i, 0);
            }
        });


    }



    @Override
    public void onClick(View v) {
        userName = username.getText().toString();
        passWord = CyptoUtils.encode(password.getText().toString());
        if (userName.isEmpty() || passWord.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please input information compeleted", Toast.LENGTH_SHORT).show();
            return;
        } else {
            new GetRESTResponse()
                    .execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.user/User.findByUserAPassword/" + userName + "/" + passWord);
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
                    if(result.equals("1"))
                    {
                        //have data sign in
                        ApplicationUser au = (ApplicationUser) getApplication();
                        au.setUsername(userName);
                        au.setSteps(0);
                        //judge local if have username
                        int k = 0;
                        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                        //get currtent time
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = new Date();
                        String date1 = sdf.format(date);

                        for (int i = 0; i< db.getuserName().size();i++)
                        {
                            if (userName.equals(db.getuserName().get(i)))
                            {
                                k = 1;
                            }
                        }
                        if(k == 0)
                        {
                            String t_password = CyptoUtils.encode(password.getText().toString());
                            User user = new User(userName,t_password,date1,31.274077f,120.753769f);
                            db.addUser(user);
                        }

                        new GetJudgeRecord().execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.report/Report.findByRepeat/" + userName + "/" + date1);


                        Intent intent = new Intent(MainActivity.this, MainHome.class);
                        Toast.makeText(MainActivity.this, "Login successful !", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                       // System.out.println("999999" + result);
                    }
                    else if (result.equals("2"))
                    {
                        //sign up first
                        Toast.makeText(MainActivity.this, "Please sign up first", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //password is wrong
                        Toast.makeText(MainActivity.this, "Please check your password", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    username.setText("");
                    password.setText("");
                }
            }
        }
    }


    private class GetJudgeRecord extends AsyncTask<String, Void, String> {
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
                        //no need insert,just update
                        return;
                    }
                    else if (result.equals("0"))
                    {
                        new GetUser().execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.user/User.findByName/"+userName);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("111111wong");
            }
        }
    }

    String insertrecord="";
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
                    //get currtent time
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();
                    String date1 = sdf.format(date);

                    String date2 = date1 + "T00:00:00+08:00";
                    String part1 = "{\"calorieGoal\":0,\"remaining\":0,\"reportDate\":\""+date2+"\",";
                    String part2 = "\"restCalorie\":0,\"stepsCalorie\":0,\"totalCalorieConsumed\":0,\"totalFoodCalorie\":0,\"totalSteps\":0,\"uid\":";
                    result = result.substring(1,result.lastIndexOf("]"));
                    String part3 = result+"}";
                    //System.out.println("55555     " + result);
                    insertrecord= part1+part2+part3;
                    //System.out.println("666666666     " + insertrecord);
                    new PostRecords().execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.report");
                    //System.out.println("77777777");
                      //  new GetUser().execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.report/Report.findByRepeat/" + username + "/" + date1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("111111wong");
            }
        }
    }


    private class PostRecords extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... args) {
            //insert data
            URL url ;
            HttpURLConnection conn =null;
            try {
                url = new URL(args[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                System.out.println(insertrecord);
                out.writeBytes(insertrecord);
                out.flush();
                out.close();

                //try to get response
                int status = conn.getResponseCode();
                //System.out.println("status is" + status);
                return status+"";

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
            return null;
        }

        // Add monsters to database after processing resource
        protected void onPostExecute(String result) {
            if (result.equals("204"))
            {
                System.out.println("insert successful !");
                //update caloriegoal
                new findUser().execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.user/User.findByName/" + userName);
                //get currtent time
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                String date1 = sdf.format(date);
                //update restcalorie
                new UpdateRestCalorie().execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.report/Report.UpdateRestCalorie/"+ userName+"/"+date1);
                //update totalCalorie
                new UpdateTotalCalorie().execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.report/Report.UpdateburnedCalorie/"+ userName+"/"+0+"/"+date1);
//                //update remaining
//                new UpdateRemaining().execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.report/Report.UpdateRemaining/" +userName+"/"+date1+"/"+0 );
                //Toast.makeText(signUp.this,"Create account successful",Toast.LENGTH_SHORT).show();
            }
        }
    }


    private class UpdateRestCalorie extends AsyncTask<String, Void, String> {
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
            if (result.equals("1") ) {
                System.out.println("update rest calorie successful" );
            }
            else
            {
                System.out.println("update rest calorie error");
            }
        }
    }

    private class findUser extends AsyncTask<String, Void, String> {
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
            if (result != null ) {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String setCalorie = jsonObject.getString("calorieSetGoal");
                    System.out.println("set calorie successfully");
                    //get currtent time
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();
                    String date1 = sdf.format(date);
                    new UpdateCalorie().execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.report/Report.UpdateCalorie/" + userName+"/"+setCalorie+"/"+date1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else
            {
                System.out.println("set calorie error");
            }
        }
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
                return conn.getResponseCode()+"";

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            if (result.equals("200") ) {
                System.out.println(" update calorie goal successful" );
                //get currtent time
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                String date1 = sdf.format(date);
                //update remaining
                new UpdateRemaining().execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.report/Report.UpdateRemaining/" + userName + "/" + date1 + "/" + 0);
            }
            else
            {
                System.out.println("update calorie goal error");
            }
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            String username_return = data.getStringExtra("username");
            String password_return = data.getStringExtra("password");
            username.setText(username_return);
            password.setText(password_return);
            signIn.setOnClickListener(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
