package com.android.nourriture.nourriture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Administrator on 2015/12/29.
 */
public class NutritionDetailActivity extends Activity {
    private ImageView back_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_nutrition);
        init();
    }

    private void init()
    {
        back_img = (ImageView)findViewById(R.id.back_img);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NutritionDetailActivity.this,MainActivity.class);
                intent.putExtra("currentIndex",2);
                startActivity(intent);
                finish();
            }
        });
    }
}
