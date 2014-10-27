package com.felixaa.fest_bong;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 10/2/2014.
 * Vil ikke fungere når man trykker LOG in, fortsatt manglende funksjon
 */
public class LoginActivity extends Activity implements OnClickListener{


    EditText username, password;
    Button login;
    HttpResponse response;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        username = (EditText) findViewById(R.id.brukernavn);
        password = (EditText) findViewById(R.id.passord);
        password.setTypeface(Typeface.DEFAULT);

        login = (Button) findViewById(R.id.login_button);
        login.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (username.getText().toString().length()<1) {
            Toast.makeText(this, "Please enter a valid email-address", Toast.LENGTH_SHORT).show();
        }
        else {
            new AsyncLogin().execute(username.getText().toString(), password.getText().toString());
        }
    }

    public void getResult(String result) {
        try {
            JSONObject jsob = new JSONObject(result);
            String error = jsob.getString("error").toString();
            if (error.equals("false")) {
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }
    }





    private class AsyncLogin extends AsyncTask<String, String, String> {

        public String resultat;

        protected String doInBackground(String... params) {
            postLogin(params[0], params[1]);
            return null;
        }


        protected void onPostExecute(String result){
            Toast.makeText(getApplicationContext(), "command sent", Toast.LENGTH_LONG).show();
            getResult(result);
        }


        protected void onProgressUpdate(String... progresjon){
            ProgressDialog progress;
            progress = new ProgressDialog(getApplicationContext());
            progress.setMessage("Loading...");
            progress.show();
        }
        public void postLogin(String email, String passord) {
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
                response = httpClient.execute(httpPost);



            } catch (ClientProtocolException e) {

            }catch (IOException e) {

            }
        }

    }



}
