package com.felixaa.fest_bong;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 10/2/2014.
 */
public class LoginActivity extends Activity implements OnClickListener{

    // Fields for buttons and EditText
    EditText username, password;
    Button login;
    Button reg;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        username = (EditText) findViewById(R.id.brukernavn);
        password = (EditText) findViewById(R.id.passord);
        password.setTypeface(Typeface.DEFAULT);

        login = (Button) findViewById(R.id.login_button);
        login.setOnClickListener(this);
        reg = (Button) findViewById(R.id.reg_button);
        reg.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == login && username.getText().toString().length()<1) {
            Toast.makeText(this, "Please enter a valid email-address", Toast.LENGTH_SHORT).show();
        }
        else if (v == reg) {
            Intent r = new Intent(this, RegActivity.class);
            startActivity(r);
        }
        else {
            new AsyncLogin().execute(username.getText().toString(), password.getText().toString());
        }
    }

    public void getResult(String result) {
        try {
            JSONObject jsob = new JSONObject(result);
            Boolean error = jsob.getBoolean("error");
            if (!error) {
                Log.v("KOMMER JEG HIT?", "jamann");
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            }
            else {
                Toast.makeText(this, "Invalid user information", Toast.LENGTH_SHORT).show();
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }
    }





    private class AsyncLogin extends AsyncTask<String,Void, String> {

        String result11 = null;
        private ProgressDialog progress;

        protected void onPreExecute(){

            progress = new ProgressDialog(LoginActivity.this);
            progress.setMessage("Loading...");
            progress.setTitle("Logging in");
            progress.show();
        }

        protected String doInBackground(String... params) {
            postLogin(params[0], params[1]);
            return result11;
        }


        protected void onPostExecute(String result){
            if (progress.isShowing()) {
                progress.dismiss();
            }
            getResult(result);
        }



        public String postLogin(String email, String passord) {
            // Lager en ny Http klient og en post header.
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://chris-felixaa.no/api/v1/login");



            try {
                // Adding data
                List<NameValuePair> valuePairs = new ArrayList<NameValuePair>(2);
                valuePairs.add(new BasicNameValuePair("email", email));
                valuePairs.add(new BasicNameValuePair("password", passord));
                httpPost.setEntity(new UrlEncodedFormEntity(valuePairs));

                // Utfører http post kallet
                HttpResponse response = httpClient.execute(httpPost);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();

                if (statusCode == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    sb.append(reader.readLine() + "\n");
                    String line = "0";
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    reader.close();

                    result11 = sb.toString();
                }
                else {
                    Log.e("HTTP: ", "Failed");
                }


            } catch (ClientProtocolException e) {

            }catch (IOException e) {

            }
            return result11;
        }

    }



}
