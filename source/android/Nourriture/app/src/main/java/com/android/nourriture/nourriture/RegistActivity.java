package com.android.nourriture.nourriture;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Administrator on 2015/12/14.
 */
public class RegistActivity extends Activity {

    private ImageView back_img;

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
    }
}
