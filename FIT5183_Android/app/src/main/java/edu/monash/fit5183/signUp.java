package edu.monash.fit5183;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import edu.monash.fit5183.Model.ServerUser;
import edu.monash.fit5183.Model.User;
import edu.monash.fit5183.Util.CyptoUtils;
import edu.monash.fit5183.Util.DatabaseHelper;

public class signUp extends AppCompatActivity implements View.OnClickListener {

    private EditText username;
    private EditText password;
    private EditText confirmPassword;
    private EditText age;
    private RadioButton male,female;
    private EditText height;
    private EditText weight;
    private Spinner level;
    private EditText steps;
    private EditText calorie;
    private Button create;
    private Button cancel;

    ServerUser serUser = null;
    private boolean same = false;
    private DatabaseHelper dh;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        username = (EditText)findViewById(R.id.signUser);
        password = (EditText)findViewById(R.id.signPassword);
        confirmPassword = (EditText)findViewById(R.id.signConfirm);
        age = (EditText)findViewById(R.id.signAge);
        male = (RadioButton)findViewById(R.id.male);
        female = (RadioButton)findViewById(R.id.female);
        height = (EditText)findViewById(R.id.signHeight);
        weight = (EditText)findViewById(R.id.signWeight);
        level = (Spinner)findViewById(R.id.signLevel);
        steps = (EditText)findViewById(R.id.signStep);
        create = (Button)findViewById(R.id.check);
        cancel = (Button)findViewById(R.id.cancel);
        calorie = (EditText)findViewById(R.id.signCalorie);

        create.setOnClickListener(this);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(signUp.this,MainActivity.class);
                startActivity(i);
            }
        });


    }

    @Override
    public void onClick(View v) {
        same = false;
        String t_username = username.getText().toString();
        if(t_username.isEmpty())
        {
            Toast.makeText(signUp.this,"Please input username",Toast.LENGTH_SHORT).show();
            username.setError("User name is required !");
            return;
        }

        if(password.getText().toString().isEmpty())
        {
            Toast.makeText(signUp.this,"Please input your password !",Toast.LENGTH_SHORT).show();
            password.setError("Password is required !");
            return;
        }
        //encrypyion password
        String t_password = CyptoUtils.encode(password.getText().toString());
        String t_confirmPassword = confirmPassword.getText().toString();

        if(!password.getText().toString().equals(t_confirmPassword))
        {
            Toast.makeText(signUp.this,"Please check your password again",Toast.LENGTH_SHORT).show();
            password.setError("Password is inconsistent !");
            return;
        }
        String gender;
        if(female.isChecked())
        {
            gender = "0";
        }
        else {
            gender = "1";
        }

        Pattern pattern = Pattern.compile("[0-9]*");
        int t_age;
        if(pattern.matcher(age.getText().toString()).matches() && !age.getText().toString().equals(""))
        {
            t_age = Integer.parseInt(age.getText().toString());
        }
        else {
            Toast.makeText(signUp.this,"Please input correct format",Toast.LENGTH_SHORT).show();
            age.setError("Please input correct format");
            return;
        }

        float t_height;
        if(pattern.matcher(height.getText().toString()).matches() && !height.getText().toString().equals(""))
        {
            t_height = Float.parseFloat(height.getText().toString());
        }
        else {
            Toast.makeText(signUp.this,"Please input correct format",Toast.LENGTH_SHORT).show();
            height.setError("Please input correct format");
            return;
        }

        float t_weight;
        if(pattern.matcher(weight.getText().toString()).matches() && !weight.getText().toString().equals(""))
        {
            t_weight = Float.parseFloat(weight.getText().toString());
        }
        else {
            Toast.makeText(signUp.this,"Please input correct format",Toast.LENGTH_SHORT).show();
            weight.setError("Please input correct format");
            return;
        }


        int t_level;
        if(pattern.matcher(level.getSelectedItem().toString()).matches())
        {

            t_level = Integer.parseInt(level.getSelectedItem().toString());
        }
        else {
            Toast.makeText(signUp.this,"Please input correct format",Toast.LENGTH_SHORT).show();
            return;
        }

        int t_steps;
        if(pattern.matcher(steps.getText().toString()).matches() && !steps.getText().toString().equals(""))
        {
            t_steps = Integer.parseInt(steps.getText().toString());
        }
        else {
            Toast.makeText(signUp.this,"Please input correct format",Toast.LENGTH_SHORT).show();
            steps.setError("Please input correct format");
            return;
        }

        int t_calorie;
        if(pattern.matcher(calorie.getText().toString()).matches() && !calorie.getText().toString().equals(""))
        {
            t_calorie = Integer.parseInt(calorie.getText().toString());
        }
        else {
            Toast.makeText(signUp.this,"Please input correct format",Toast.LENGTH_SHORT).show();
            calorie.setError("Please input correct format");
            return;
        }

        //get currtent time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String date1 = sdf.format(date);
        user = new User(t_username,t_password,date1,31.274077f,120.753769f);
        dh = new DatabaseHelper(getApplicationContext());

        boolean judge_user = true;
        for (int i = 0; i< dh.getuserName().size();i++)
        {
            if (t_username.equals(dh.getuserName().get(i)))
            {
                Toast.makeText(signUp.this,"Please input another username",Toast.LENGTH_SHORT).show();
                username.setError("User is already having !");
                judge_user = false;
                return;
            }
        }
        if (judge_user == true)
        {
            //serUser = new ServerUser(t_username,password.getText().toString(),t_age,t_height,t_weight,gender,t_level,t_steps,t_calorie);
            serUser = new ServerUser(t_username,t_password,t_age,t_height,t_weight,gender,t_level,t_steps,t_calorie);
            //find if have same user
            new GetRESTResponse_compare().execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.user/User.findByUserAPassword/" + t_username + "/" + t_password);
        }
    }



    private class GetRESTResponse_compare extends AsyncTask<String, Void, String> {
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

        // Add monsters to database after processing resource
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    if (result.equals("1") || result.equals("3"))
                    {
                        same = true;
                        Toast.makeText(signUp.this,"This name has already been registered",Toast.LENGTH_SHORT).show();
                    }

                    //insert data
                    if (same == false)
                    {
                        //insert user
                        GetRESTResponse getRESTResponse = new GetRESTResponse();
                        getRESTResponse.execute("http://172.16.120.179:8080/id27315789/webresources/com.entity.user");
                        //insert user in local
                        dh.addUser(user);

                        //transmit username and password to MainActivity
                        Intent intent = new Intent();
                        intent.putExtra("username", username.getText().toString());
                        intent.putExtra("password", password.getText().toString());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                }
            }
        }
    }




    private class GetRESTResponse extends AsyncTask<String, Void, String> {
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

                Gson gson = new Gson();
                String jsonData = gson.toJson(serUser);
                System.out.println(jsonData);
                out.writeBytes(jsonData);
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
                //System.out.println("123456");
                Toast.makeText(signUp.this,"Create account successful",Toast.LENGTH_SHORT).show();
            }
        }
    }



}
