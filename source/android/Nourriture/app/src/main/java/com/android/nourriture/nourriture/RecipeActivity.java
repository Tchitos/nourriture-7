package com.android.nourriture.nourriture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.nurriture.fragment.ListViewFragment;
import com.android.nuttriture.adapter.ListViewAdapter;

/**
 * Created by Administrator on 2015/12/18.
 */
public class RecipeActivity extends FragmentActivity {
    private String[] recipe = {"Health","Sickness","Religion","Diet","Crowd"};
    private ListView listView;
    private ListViewAdapter recipeAdapter;
    private ListViewFragment listViewFragment;
    public static int mPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recipe);
        initView();
        getBack();
    }
    private void getBack(){
        ImageView back = (ImageView)findViewById(R.id.recipe_back_img);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(RecipeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void initView(){
        listView = (ListView)findViewById(R.id.recipeListview);
        recipeAdapter = new ListViewAdapter(this,recipe,"recipeActivity");
        listView.setAdapter(recipeAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;
                recipeAdapter.notifyDataSetChanged();
                for (int i = 0; i < recipe.length; i++){
                    listViewFragment = new ListViewFragment();
                    FragmentTransaction fragmentTransaction;
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();

                    FragmentTransaction replace = fragmentTransaction.replace(R.id.recipelistViewFragment, listViewFragment);
                    Bundle bundle = new Bundle();
                    bundle.putString(ListViewFragment.TAG, recipe[position]);
                    listViewFragment.setArguments(bundle);
                    fragmentTransaction.commit();
                }
            }
        }
        );

        //ListViewFragment
        listViewFragment = new ListViewFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.recipelistViewFragment, listViewFragment);

        //ͨbundlelistViewFragment
        Bundle bundle = new Bundle();
        bundle.putString(ListViewFragment.TAG, recipe[mPosition]);
        listViewFragment.setArguments(bundle);
        fragmentTransaction.commit();

    }
}
