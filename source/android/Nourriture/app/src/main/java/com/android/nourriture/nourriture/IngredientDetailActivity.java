package com.android.nourriture.nourriture;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/12/29.
 */
public class IngredientDetailActivity extends Activity {
    private ImageView back_img;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_ingredient);
        init();
    }

    private void init()
    {
        String nameString = getIntent().getExtras().getString("ingredientName");
        back_img = (ImageView)findViewById(R.id.back_img);
        name = (TextView)findViewById(R.id.name);
        name.setText(nameString);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
