package edu.monash.fit5183.CallServer;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Think on 4/24/2016.
 */
public class UpdateRemaining extends AsyncTask<String, Void, String> {
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
            System.out.println("update remaining  successful" );
        }
        else
        {
            System.out.println("update remaining error");
        }
    }
}