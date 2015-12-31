package com.android.nourriture.nourriture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.nurriture.util.HttpMethod;
import com.android.nurriture.util.HttpUtil;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/12/14.
 */
public class LoginActivity extends Activity {

    private ImageView back_img;
    private EditText username,password;
    private TextView login_button;
    private SharedPreferences sp;
    String usernameString = null;
    String passwordString = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();
    }

    private void init()
    {
        sp = getSharedPreferences("userinfo", 0);
        back_img = (ImageView)findViewById(R.id.back_img);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        login_button = (TextView)findViewById(R.id.login_button);

        username.setText(sp.getString("username", null));
        password.setText(sp.getString("password", null));

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private boolean login()
    {
        usernameString = username.getText().toString();
        passwordString = password.getText().toString();
        /*if (usernameString == null || usernameString.equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Please enter the username!", Toast.LENGTH_SHORT);
            toast.show();
        } else if (passwordString == null || passwordString.equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Please enter the password!", Toast.LENGTH_SHORT);
            toast.show();
        } else {*/
            //urlConn();
        Map<String, String> map = new HashMap<String, String>();
        map.put("username", usernameString);
        map.put("password", passwordString);
        HttpUtil connectNet = new HttpUtil(
                "/login",
                HttpMethod.POST, map) {
            @Override
            protected void getResult(String result) {
                Toast.makeText(getApplicationContext(), "Connect Server API success!="+result,
                        Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String statusCode = jsonObject.getString("statusCode");
                    String value = jsonObject.getString("value");
                    Toast.makeText(getApplicationContext(), "statusCode="+statusCode+" value:"+value,
                            Toast.LENGTH_SHORT).show();
                    // 登录成功，开始计时
                    //startTimer();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        connectNet.execute();
            sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
            sp.edit().putString("username", usernameString).commit();
            sp.edit().putString("password", passwordString).commit();
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("currentIndex",3);
            startActivity(intent);
            finish();
        /*}*/
        return true;
    }

    protected void urlConn()
    {
        try{
            String urlString = "http://104.236.38.237:3000/login";
            URL url = new URL(urlString);
            HttpURLConnection httpconn = (HttpURLConnection) url.openConnection();
            httpconn.setRequestMethod("POST");
            httpconn.setDoOutput(true);
            httpconn.setDoInput(true);
            httpconn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpconn.connect();

            DataOutputStream out = new DataOutputStream(httpconn
                    .getOutputStream());
            String content = "username=hlh";
            content +="&password=hlh";
            out.writeBytes(content);

            out.flush();
            out.close();
            if (httpconn.getResponseCode() == 200) {
                Toast.makeText(getApplicationContext(), "Connect Server API success!",
                        Toast.LENGTH_SHORT).show();
                InputStream is = httpconn.getInputStream();
                byte[] data = readStream(is);
                String json = new String(data);
            }
            }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Connect Server Failed!", Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    private static byte[] readStream(InputStream inputStream) throws Exception {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            bout.write(buffer, 0, len);
        }
        bout.close();
        inputStream.close();
        return bout.toByteArray();
    }
}
