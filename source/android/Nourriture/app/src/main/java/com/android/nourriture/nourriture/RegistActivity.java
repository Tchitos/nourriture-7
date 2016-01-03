package com.android.nourriture.nourriture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.nurriture.util.PostRequestAPI;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2015/12/14.
 */
public class RegistActivity extends Activity {

    private ImageView back_img;

    private EditText username,password, email;
    private TextView register_button;
    String usernameString, passwordString, emailString = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist);
        init();
    }

    private void init()
    {
        back_img = (ImageView)findViewById(R.id.back_img);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        email = (EditText)findViewById(R.id.email);
        register_button = (TextView)findViewById(R.id.registBtn);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }

    private void register() {
        usernameString = username.getText().toString();
        passwordString = password.getText().toString();
        emailString = email.getText().toString();

        if (usernameString == null || usernameString.equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Please enter a username", Toast.LENGTH_SHORT);
            toast.show();
        } else if (passwordString == null || passwordString.equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Please enter a password", Toast.LENGTH_SHORT);
            toast.show();
        } else if (emailString == null || emailString.equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Please enter an email", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            PostRequestAPI api = new PostRequestAPI(this);
            api.setUsername(usernameString);
            api.setPassword(passwordString);
            api.setEmail(emailString);
            api.setRequest("register");
            api.execute();

            try {
                api.get(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }

            if (api.getSuccess() == false) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        api.getError(), Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Register complete", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}
