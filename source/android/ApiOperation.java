package com.ewample.nourriture;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thibault on 10/12/15.
 */

public class ApiOperation extends AsyncTask<String, Void, Void> {

    final HttpClient httpClient = new DefaultHttpClient();
    String content;
    String error;
    String data;
    ProgressDialog progressDialog = new ProgressDialog(MainActivity.activity);

    EditText username = (EditText) ((Activity)MainActivity.activity).findViewById(R.id.TFUsername);
    EditText password = (EditText) ((Activity)MainActivity.activity).findViewById(R.id.TFPassword);

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog.setTitle("Please wait...");
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(String... params) {

        Log.v("TOTO", "Username = " + username.getText());
        Log.v("TOTO", "Password = " + password.getText());

        String url = "http://104.236.38.237/login";
        BufferedReader inStream = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("username", username.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("password", password.getText().toString()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            String responseBody = EntityUtils.toString(response.getEntity());

            Log.v("TOTO", "Status code : " + response.getStatusLine().getStatusCode());
            Log.v("TOTO", "Response : " + responseBody);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        progressDialog.hide();

    }
}
