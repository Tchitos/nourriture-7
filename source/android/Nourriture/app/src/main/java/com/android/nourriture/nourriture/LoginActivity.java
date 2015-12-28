package com.android.nourriture.nourriture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        sp = getSharedPreferences("userinfo",0);
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
        if (usernameString == null || usernameString.equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Please enter the username!", Toast.LENGTH_SHORT);
            toast.show();
        } else if (passwordString == null || passwordString.equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Please enter the password!", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
            sp.edit().putString("username", usernameString).commit();
            sp.edit().putString("password", passwordString).commit();
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("currentIndex",3);
            startActivity(intent);
            finish();
        }
        return true;
    }
}
