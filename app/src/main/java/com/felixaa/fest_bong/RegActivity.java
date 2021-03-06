package com.felixaa.fest_bong;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
 * Created by Chris on 11/2/2014.
 */
public class RegActivity extends Activity implements View.OnClickListener {
    EditText brukerNavn, email, passord;
    Button registrer;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        brukerNavn = (EditText) findViewById(R.id.brukernavn_reg);
        email = (EditText) findViewById(R.id.email_reg);
        passord = (EditText) findViewById(R.id.passord_reg);

        registrer = (Button) findViewById(R.id.reg_post_button);
        registrer.setOnClickListener(this);
    }


    public void getResult(String result) {
        try {
            JSONObject jsob = new JSONObject(result);
            Boolean error = jsob.getBoolean("error");
            if (!error) {
                Log.v("KOMMER JEG HIT?", "jamann");
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
            }
            else {
                Toast.makeText(this, "Invalid user information", Toast.LENGTH_SHORT).show();
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (brukerNavn.getText().toString().length()<1) {
            Toast.makeText(this, "Please enter a valid username, password and email", Toast.LENGTH_SHORT).show();
        }
        else {
            new AsyncLogin().execute(brukerNavn.getText().toString(), email.getText().toString(), passord.getText().toString());
        }


    }


    private class AsyncLogin extends AsyncTask<String,Void, String> {

        String result11 = null;
        private ProgressDialog progress;

        protected void onPreExecute(){

            progress = new ProgressDialog(RegActivity.this);
            progress.setMessage("Loading...");
            progress.setTitle("Enrollment");
            progress.show();
        }

        protected String doInBackground(String... params) {
            postLogin(params[0], params[1], params[2]);
            return result11;
        }


        protected void onPostExecute(String result){
            if (progress.isShowing()) {
                progress.dismiss();
            }
            getResult(result);
        }



        public String postLogin(String brukerNavn, String email, String passord) {
            // Lager en ny Http klient og en post header.
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://chris-felixaa.no/api/v1/register");



            try {
                // Adding data
                List<NameValuePair> valuePairs = new ArrayList<NameValuePair>(3);
                valuePairs.add(new BasicNameValuePair("email", email));
                valuePairs.add(new BasicNameValuePair("password", passord));
                valuePairs.add(new BasicNameValuePair("name", brukerNavn));
                httpPost.setEntity(new UrlEncodedFormEntity(valuePairs));

                // Utfører http post kallet
                HttpResponse response = httpClient.execute(httpPost);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();

                if (statusCode == 201) {
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

                else if (statusCode == 400)  {
                    BufferedReader ureader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"iso-8859-1"), 8);
                    StringBuilder sbu = new StringBuilder();
                    sbu.append(ureader.readLine() + "\n");
                    String line = "0";
                    while ((line = ureader.readLine())  != null)  {
                        sbu.append(line + "\n");
                    }
                    ureader.close();

                    result11 = sbu.toString();
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
