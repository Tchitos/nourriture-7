package com.android.nurriture.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import com.android.nourriture.nourriture.MainActivity;

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

public class PostRequestAPI extends AsyncTask<String, Void, Void> {

    String username;
    String password;
    String url = "http://104.236.38.237";
    Boolean success = false;
    String error = null;


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

     //   progressDialog.setTitle("Please wait...");
   //     progressDialog.show();
    }

    @Override
    protected Void doInBackground(String... params) {

        Log.v("TOTO", "Username = " + username);
        Log.v("TOTO", "Password = " + password);

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("username", username));
            nameValuePairs.add(new BasicNameValuePair("password", password));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            String responseBody = EntityUtils.toString(response.getEntity());

            Log.v("TOTO", "Status code : " + response.getStatusLine().getStatusCode());
            Log.v("TOTO", "Response : " + responseBody);
            if (response.getStatusLine().getStatusCode() == 200){
                this.success = true;
            } else {
                this.error = responseBody;
            }
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

       // progressDialog.hide();

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRequest(String req) {
        url += req;
    }

    public boolean getSuccess() {
        return (this.success);
    }

    public String getError() {
        return (this.error);
    }
}
